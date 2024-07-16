package com.criptografia.domain.usuario;

import java.util.Date;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Credenciais {

	@NotNull
	private String email;
	
	@NotNull
	private String senha;
	
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

    public Credenciais() {
		super();
	}

	public Credenciais(@NotNull String email, @NotNull String senha, @NotNull Boolean habilitado,
			@NotNull Date dataUltimaRedefinicaoDeSenha, @NotNull boolean contaNaoExpirada, @NotNull boolean contaNaoBloqueada,
			@NotNull boolean credencialNaoExpirada) {
		super();
		this.email = email;
		this.senha = senha;
		this.habilitado = habilitado;
		this.dataUltimaRedefinicaoDeSenha = dataUltimaRedefinicaoDeSenha;
		this.contaNaoExpirada = contaNaoExpirada;
		this.contaNaoBloqueada = contaNaoBloqueada;
		this.credencialNaoExpirada = credencialNaoExpirada;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
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
}
