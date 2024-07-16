package com.criptografia.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.criptografia.domain.usuario.Usuario;
import com.criptografia.domain.usuario.UsuarioRepository;

@Component
public class UsuarioLogado {
	
	@Autowired
	private UsuarioRepository repository;

	public Usuario getUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ResourceOwner usuario = (ResourceOwner) authentication.getPrincipal();
		return repository.findById(usuario.getId()).orElseThrow();
	}
}
