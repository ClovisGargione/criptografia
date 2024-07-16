package com.criptografia.domain.pagamento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.criptografia.domain.usuario.Usuario;
import com.criptografia.domain.usuario.UsuarioLogado;

@RestController
@RequestMapping("/secure/dados-pagamento")
public class DadosPagamentoController {
	
	@Autowired
	private DadosPagamentoRepository dadosPagamentoRepository;
	
	@Autowired
	private UsuarioLogado usuarioLogado;
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody DadosPagamento dadosPagamento) {
		Usuario usuario = usuarioLogado.jwt();
		dadosPagamento.setUsuario(usuario);
		dadosPagamentoRepository.save(dadosPagamento);
		return ResponseEntity.ok().build();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable(name = "id", required = true) Long id){
		DadosPagamento dadosPagamento = dadosPagamentoRepository.findById(id).orElse(null);
		return ResponseEntity.ok(dadosPagamento);
	}
	
	@GetMapping
	public ResponseEntity<?> buscarPorUsuario(){
		Usuario usuario = usuarioLogado.jwt();
		List<DadosPagamento> dadosPagamentos = dadosPagamentoRepository.findByUsuario(usuario);
		dadosPagamentos.stream().forEach(d -> d.setUsuario(null));
		return ResponseEntity.ok(dadosPagamentos);
	}
	
	@DeleteMapping
	public ResponseEntity<?> remover(@RequestParam(name = "id") Long id) {
		dadosPagamentoRepository.deleteById(id);
		return ResponseEntity.ok().build();
		
	}

}
