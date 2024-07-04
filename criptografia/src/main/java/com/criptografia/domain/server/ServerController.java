package com.criptografia.domain.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/public")
public class ServerController {
	
	@Autowired
	private PasswordEncoder userPasswordEncoder;
	
	@GetMapping("/encrypt/{password}")
    public String encrypt(@PathVariable("password") String password) {
        
        return userPasswordEncoder.encode(password);
    }
	
	@GetMapping("/server")
	public	String	user(HttpServletRequest	request)	{	
		return request.getServerName()+":"+request.getServerPort(); 
	}

}
