package com.criptografia.domain.pagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Endereco {

	@NotEmpty
	@Column(length = 500, nullable = false)
	private String cep;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String rua;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String numero;
	
	@Column(length = 500, nullable = true)
	private String complemento;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String bairro;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String cidade;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String estado;
	
	public Endereco(@NotNull String cep, @NotNull String rua, @NotNull String numero, String complemento,
			@NotNull String bairro, @NotNull String cidade, @NotNull String estado) {
		super();
		this.cep = cep;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	public Endereco() {
		super();
	}
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
