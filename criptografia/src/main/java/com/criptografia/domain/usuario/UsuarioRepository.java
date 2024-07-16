package com.criptografia.domain.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.credenciais.email = ?1")
	Optional<Usuario> findByEmail(@Param(value = "email") String email);
}
