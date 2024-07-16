package com.authentication.domain.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.security.jpa.JpaOAuth2AuthorizationService;


@RestController
@RequestMapping("/api/v2/authorization")
public class AuthorizationController {
	
	@Autowired
	private JpaOAuth2AuthorizationService authorizationService;
	
	@GetMapping("/info-token")
    public ResponseEntity<?> info(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		String[] tokenpParts = authorization.split(" ");
		return new ResponseEntity<>(authorizationService.findByToken(tokenpParts[1]), HttpStatus.OK);
	}

}
