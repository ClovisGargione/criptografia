package com.criptografia.domain.rest;

import java.net.URI;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.criptografia.domain.usuario.dto.UsuarioDTO;

@Service
public class RestService {

	public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
		RestTemplate restTemplate = new RestTemplate();
		String endpointRegistroUsuario = "http://localhost:8084/api/public/usuario";
		
		RequestEntity<Object> request = new RequestEntity<>(usuarioDTO, getHeader(), HttpMethod.POST, URI.create(endpointRegistroUsuario));
		ResponseEntity<UsuarioDTO> responseEntity = restTemplate.exchange(request, UsuarioDTO.class);
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
		throw new RuntimeException("Erro ao inserir novo usuário");
	}
	
	public HttpHeaders getHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
        return httpHeaders;
    }
}
