package com.giuseppe.hotel.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.cors(cors -> cors.configurationSource(corsConfiguratitonSource()))
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
					.requestMatchers(org.springframework.http.HttpMethod.POST, "/api/customers").permitAll()
					.requestMatchers(org.springframework.http.HttpMethod.GET, "/api/rooms").permitAll()
					.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
					.requestMatchers("/api/rooms/**").hasRole("ADMIN")
					.anyRequest().authenticated())
					.httpBasic(org.springframework.security.config.Customizer.withDefaults());
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfiguratitonSource() {
		
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOrigins(List.of("http://localhost:5173", "http://127.0.0.1:5173"));
		config.setAllowedMethods(List.of("*"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
