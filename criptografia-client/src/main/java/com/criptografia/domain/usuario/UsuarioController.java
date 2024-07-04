package com.criptografia.domain.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.criptografia.configuration.ResourceOwner;
import com.criptografia.domain.usuario.dto.AuthorityDTO;
import com.criptografia.domain.usuario.dto.DadosDeRegistro;
import com.criptografia.domain.usuario.dto.UsuarioDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/registrar")
public class UsuarioController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
		
	@GetMapping
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("usuario/cadastro");

        DadosDeRegistro dadosDeRegistro = new DadosDeRegistro();
        mv.addObject("dadosDeRegistro", dadosDeRegistro);

        return mv;
    }
	
	@PostMapping
    public ModelAndView registrar(@Valid DadosDeRegistro dadosDeRegistro, BindingResult bindingResult, HttpServletRequest request) {
        
		List<AuthorityDTO> authorities = new ArrayList<>();
		authorities.addAll(dadosDeRegistro.getAuthorities());
		Credenciais credenciais = new Credenciais(dadosDeRegistro.getEmail(),
				dadosDeRegistro.getSenha(), dadosDeRegistro.isHabilitado(), new Date(), dadosDeRegistro.isContaNaoExpirada(), dadosDeRegistro.isContaNaoBloqueada(), dadosDeRegistro.isCredencialNaoExpirada());
		UsuarioDTO usuarioDTO = new UsuarioDTO(dadosDeRegistro.getNome(), credenciais, authorities);
		
		//enviar para servidor

        mantemUsuarioAutenticado(usuarioDTO, request);
        
        return new ModelAndView("redirect:/minhaconta/principal");
    }
	
	/**
	 * Esse método é usado apenas para adicionar o usuário recem cadastrado na
	 * sessão do Spring Security para que o usuário não precise se autenticar assim
	 * que se cadastra.
	 * 
	 * @param authenticationManager
	 * @param usuario
	 */
	private void mantemUsuarioAutenticado(UsuarioDTO usuarioDTO, HttpServletRequest request) {
		List<Authority> authorities = new ArrayList<>();
		authorities.addAll(mapToAutorities(usuarioDTO.getAuthorities()));
		Usuario usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getCredenciais(), authorities);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new ResourceOwner(usuario),
				usuario.getCredenciais().getSenha());
		Authentication auth = authenticationManager.authenticate(token);
		SecurityContext sc = SecurityContextHolder.getContext();
	    sc.setAuthentication(auth);
	    HttpSession session = request.getSession(true);
	    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
	}
	
	private Collection<? extends Authority> mapToAutorities(List<AuthorityDTO> authorities) {
		return authorities.stream().map(authority -> new Authority(authority.getName())).collect(Collectors.toList());
	}
}
