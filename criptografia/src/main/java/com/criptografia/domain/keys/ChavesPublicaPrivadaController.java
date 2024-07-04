package com.criptografia.domain.keys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class ChavesPublicaPrivadaController {
	
	/**
	 * Local da chave privada no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PRIVADA = "keys/private.key";

	/**
	 * Local da chave pública no sistema de arquivos.
	 */
	public static final String PATH_CHAVE_PUBLICA = "keys/public.key";

	@GetMapping("/gerar")
	public ResponseEntity<?> gerarChaves(){
		if(!GerarChavesPublicaPrivada.verificaSeExisteChavesNoSO()) {
			GerarChavesPublicaPrivada.generateRsaKey();
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/download/publica")
	public ResponseEntity<?> downloadPublica() throws FileNotFoundException{
		InputStreamResource inputStream = new InputStreamResource(new FileInputStream(PATH_CHAVE_PUBLICA));
		File file = new File(PATH_CHAVE_PUBLICA);
		String headerValue = "attachment; filename=\"" + file.getName() + "\"";
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
				.contentLength(file.length())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(inputStream);
	}
	
	@GetMapping("/download/privada")
	public ResponseEntity<?> downloadPrivada() throws FileNotFoundException{
		InputStreamResource inputStream = new InputStreamResource(new FileInputStream(PATH_CHAVE_PRIVADA));
		File file = new File(PATH_CHAVE_PRIVADA);
		String headerValue = "attachment; filename=\"" + file.getName() + "\"";
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
				.contentLength(file.length())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(inputStream);
	}
}
