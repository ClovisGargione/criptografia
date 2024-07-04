package com.criptografia.domain.usuario.dto;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class DadosDeRegistro {

	@NotEmpty(message = "O nome deve ser preenchido")
	private String nome;
	
	@NotEmpty(message = "A senha deve ser preenchida")
	private String senha;
	
	@NotEmpty(message = "O e-mail deve ser preenchido")
	private String email;
	
	@NotNull
    private boolean habilitado;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date dataUltimaRedefinicaoDeSenha;
    
    @NotNull
    private boolean contaNaoExpirada;
    
    @NotNull
    private boolean contaNaoBloqueada;
    
    @NotNull
    private boolean credencialNaoExpirada;
    
    @NotNull
    private List<AuthorityDTO> authorities;

	public DadosDeRegistro() {
		super();
	}

	public DadosDeRegistro(@NotEmpty(message = "O nome deve ser preenchido") String nome,
			@NotEmpty(message = "A senha deve ser preenchida") String senha,
			@NotEmpty(message = "O e-mail deve ser preenchido") String email, @NotNull boolean habilitado,
			@NotNull Date dataUltimaRedefinicaoDeSenha, @NotNull boolean contaNaoExpirada,
			@NotNull boolean contaNaoBloqueada, @NotNull boolean credencialNaoExpirada,
			@NotNull List<AuthorityDTO> authorities) {
		super();
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.habilitado = habilitado;
		this.dataUltimaRedefinicaoDeSenha = dataUltimaRedefinicaoDeSenha;
		this.contaNaoExpirada = contaNaoExpirada;
		this.contaNaoBloqueada = contaNaoBloqueada;
		this.credencialNaoExpirada = credencialNaoExpirada;
		this.authorities = authorities;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Date getDataUltimaRedefinicaoDeSenha() {
		return dataUltimaRedefinicaoDeSenha;
	}

	public void setDataUltimaRedefinicaoDeSenha(Date dataUltimaRedefinicaoDeSenha) {
		this.dataUltimaRedefinicaoDeSenha = dataUltimaRedefinicaoDeSenha;
	}

	public boolean isContaNaoExpirada() {
		return contaNaoExpirada;
	}

	public void setContaNaoExpirada(boolean contaNaoExpirada) {
		this.contaNaoExpirada = contaNaoExpirada;
	}

	public boolean isContaNaoBloqueada() {
		return contaNaoBloqueada;
	}

	public void setContaNaoBloqueada(boolean contaNaoBloqueada) {
		this.contaNaoBloqueada = contaNaoBloqueada;
	}

	public boolean isCredencialNaoExpirada() {
		return credencialNaoExpirada;
	}

	public void setCredencialNaoExpirada(boolean credencialNaoExpirada) {
		this.credencialNaoExpirada = credencialNaoExpirada;
	}

	public List<AuthorityDTO> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthorityDTO> authorities) {
		this.authorities = authorities;
	}
}
