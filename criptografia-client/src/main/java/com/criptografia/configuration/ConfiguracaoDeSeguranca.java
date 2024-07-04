package com.criptografia.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ConfiguracaoDeSeguranca {

	@Autowired
	private PasswordEncoder userPasswordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
		String[] caminhosPermitidos = new String[] { "/erro", "/home", "/login", "/registrar", "/webjars/**", "/js/**",
		"/jquery*"};
		httpSecurity
		.authorizeHttpRequests(req -> 
            req.requestMatchers(caminhosPermitidos).permitAll()
            .anyRequest().authenticated()
        )
			.httpBasic(withDefaults())
            .formLogin(form -> form.defaultSuccessUrl("/home").loginPage("/login").failureUrl("/login"))
            .logout((logout) -> logout.permitAll().deleteCookies("CRIPTSESSIONID"))
            .csrf((c) -> c.disable());
        return httpSecurity.build();
	}
	
	private AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(userPasswordEncoder);
		return authProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authProvider());
		return authenticationManagerBuilder.build();
	}
}
