package com.criptografia.domain.usuario.dto;

import com.criptografia.domain.usuario.AuthorityName;

import jakarta.validation.constraints.NotNull;

public class AuthorityDTO {

	private AuthorityName name;
	
	public AuthorityDTO() {
		super();
	}

	public AuthorityDTO(@NotNull AuthorityName name) {
		super();
		this.name = name;
	}

	public AuthorityName getName() {
		return name;
	}

	public void setName(AuthorityName name) {
		this.name = name;
	}
}
