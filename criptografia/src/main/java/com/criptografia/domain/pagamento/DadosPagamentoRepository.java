package com.criptografia.domain.pagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criptografia.domain.usuario.Usuario;

public interface DadosPagamentoRepository extends JpaRepository<DadosPagamento, Long> {

	List<DadosPagamento> findByUsuario(Usuario usuario);
}
