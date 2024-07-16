package com.criptografia.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.criptografia.domain.usuario.Usuario;
import com.criptografia.domain.usuario.UsuarioRepository;

@Service
public class DadosDoUsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = repository.findByEmail(username);
		if (usuario.isPresent()) {
			return new ResourceOwner(usuario.get());
		} else {
			throw new UsernameNotFoundException("Usuario não autorizado");
		}
	}

}
