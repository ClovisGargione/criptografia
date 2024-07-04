package com.criptografia.domain.usuario;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.criptografia.domain.keys.GerarChavesPublicaPrivada;
import com.criptografia.domain.usuario.dto.AuthorityDTO;
import com.criptografia.domain.usuario.dto.UsuarioDTO;

@RestController
@RequestMapping
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncoder userPasswordEncoder;
	
	@Autowired
	private UsuarioLogado usuarioLogado;

	@PostMapping("/public/usuario")
	public ResponseEntity<?> registrar(@RequestBody UsuarioDTO usuarioDto){
		List<Authority> authorities = new ArrayList<>();
		authorities.addAll(mapToAutorities(usuarioDto.getAuthorities()));
		Credenciais credenciais = new Credenciais(usuarioDto.getCredenciais().getEmail(), userPasswordEncoder.encode(usuarioDto.getCredenciais().getSenha()), usuarioDto.getCredenciais().isHabilitado(), usuarioDto.getCredenciais().getDataUltimaRedefinicaoDeSenha(), usuarioDto.getCredenciais().isContaNaoExpirada(), usuarioDto.getCredenciais().isContaNaoBloqueada(), usuarioDto.getCredenciais().isCredencialNaoExpirada());
		Usuario usuario = new Usuario(usuarioDto.getNome(), credenciais, authorities);
		repository.save(usuario);
		usuarioDto.setId(usuario.getId());
		return ResponseEntity.ok(usuarioDto);
		
	}
	
	
	@GetMapping("/secure/usuario")
	public ResponseEntity<?> buscar(@RequestHeader(value="Authorization", defaultValue = "Bearer ") String token, @RequestParam(name = "email") String email){
		Optional<Usuario> usuario = repository.findByEmail(email);
		return ResponseEntity.ok(usuario);
		
	}
	
	@DeleteMapping("/secure/usuario")
	public ResponseEntity<?> remover(@RequestBody Long id){
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/secure/usuario-logado")
	public ResponseEntity<?> usuarioLogado(){
		return ResponseEntity.ok(usuarioLogado.jwt());
	}
	
	@PostMapping("/public/teste-cripto")
	public ResponseEntity<?> cripto(@RequestBody Cripto texto) throws Exception{
		final PrivateKey chavePrivada = GerarChavesPublicaPrivada.getPrivateKey(GerarChavesPublicaPrivada.PATH_CHAVE_PRIVADA);
		byte[] decodedString = Base64.getDecoder().decode(texto.getTexto().getBytes(StandardCharsets.UTF_8));
		final String textoPuro = GerarChavesPublicaPrivada.decriptografa(decodedString, chavePrivada);
		final PublicKey chavePublica = GerarChavesPublicaPrivada.getPublicKey(GerarChavesPublicaPrivada.PATH_CHAVE_PUBLICA);
		byte[] textoCriptografado = GerarChavesPublicaPrivada.criptografa(textoPuro, chavePublica);
		Base64.getEncoder().encode(textoCriptografado);
		return ResponseEntity.ok(Base64.getEncoder().encode(textoCriptografado));
		
	}
	

	private Collection<? extends Authority> mapToAutorities(List<AuthorityDTO> authorities) {
		return authorities.stream().map(authority -> new Authority(authority.getName())).collect(Collectors.toList());
	}
	
	
	private static class Cripto {
		
		private String texto;
		
		public Cripto() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Cripto(String texto) {
			super();
			this.texto = texto;
		}

		public String getTexto() {
			return texto;
		}

		public void setTexto(String texto) {
			this.texto = texto;
		}
	}
}