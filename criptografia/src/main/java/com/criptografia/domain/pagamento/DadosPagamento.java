package com.criptografia.domain.pagamento;

import com.criptografia.domain.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class DadosPagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String numeroCartao;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String nomeCartao;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String validadeCartao;
	
	@NotEmpty
	@Column(length = 500, nullable = false)
	private String cvvCartao;
	
	private Endereco enderecoCobranca;

	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	
	public DadosPagamento() {
		super();
	}

	public DadosPagamento(String numeroCartao, String nomeCartao, String validadeCartao, String cvvCartao,
			Endereco enderecoCobranca) {
		super();
		this.numeroCartao = numeroCartao;
		this.nomeCartao = nomeCartao;
		this.validadeCartao = validadeCartao;
		this.cvvCartao = cvvCartao;
		this.enderecoCobranca = enderecoCobranca;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public String getValidadeCartao() {
		return validadeCartao;
	}

	public void setValidadeCartao(String validadeCartao) {
		this.validadeCartao = validadeCartao;
	}

	public String getCvvCartao() {
		return cvvCartao;
	}

	public void setCvvCartao(String cvvCartao) {
		this.cvvCartao = cvvCartao;
	}

	public Endereco getEnderecoCobranca() {
		return enderecoCobranca;
	}

	public void setEnderecoCobranca(Endereco enderecoCobranca) {
		this.enderecoCobranca = enderecoCobranca;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
