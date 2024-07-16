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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.criptografia.CriptografiaService;
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
		String email = CriptografiaService.decriptografar(usuarioDto.getCredenciais().getEmail());
		if(repository.findByEmail(email).isPresent()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Usuário já cadastrado!");
		}
		String senha = CriptografiaService.decriptografar(usuarioDto.getCredenciais().getSenha());
		Credenciais credenciais = new Credenciais(email, userPasswordEncoder.encode(senha), usuarioDto.getCredenciais().isHabilitado(), usuarioDto.getCredenciais().getDataUltimaRedefinicaoDeSenha(), usuarioDto.getCredenciais().isContaNaoExpirada(), usuarioDto.getCredenciais().isContaNaoBloqueada(), usuarioDto.getCredenciais().isCredencialNaoExpirada());
		List<Authority> authorities = new ArrayList<>();
		authorities.addAll(mapToAutorities(usuarioDto.getAuthorities()));
		String nome = CriptografiaService.decriptografar(usuarioDto.getNome());
		Usuario usuario = new Usuario(nome, credenciais, authorities);
		repository.save(usuario);
		return ResponseEntity.ok().build();
		
	}
	
	
	@GetMapping("/secure/usuario")
	public ResponseEntity<?> buscar(@RequestParam(name = "email") String email){
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
		Usuario usuario =  usuarioLogado.jwt();
		usuario.setNome(new String(CriptografiaService.criptografar(usuario.getNome())));
		usuario.getCredenciais().setEmail(new String(CriptografiaService.criptografar(usuario.getCredenciais().getEmail())));
		usuario.getCredenciais().setSenha(null);
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping("/public/teste-cripto")
	public ResponseEntity<?> cripto(@RequestBody Cripto texto) throws Exception{
		final PrivateKey chavePrivada = GerarChavesPublicaPrivada.getPrivateKey(GerarChavesPublicaPrivada.PATH_CHAVE_PRIVADA);
		byte[] decodedString = Base64.getDecoder().decode(texto.getTexto().getBytes(StandardCharsets.UTF_8));
		final String textoPuro = GerarChavesPublicaPrivada.decriptografa(decodedString, chavePrivada);
		final PublicKey chavePublica = GerarChavesPublicaPrivada.getPublicKey(GerarChavesPublicaPrivada.PATH_CHAVE_PUBLICA);
		byte[] textoCriptografado = GerarChavesPublicaPrivada.criptografa(textoPuro, chavePublica);
		return ResponseEntity.ok(Base64.getEncoder().encode(textoCriptografado));
		
	}
	

	private Collection<? extends Authority> mapToAutorities(List<AuthorityDTO> authorities) {
		return authorities.stream().map(authority -> new Authority(authority.getName())).collect(Collectors.toList());
	}
	
	
	private static class Cripto {
		
		private String texto;
		
		public String getTexto() {
			return texto;
		}
	}
}