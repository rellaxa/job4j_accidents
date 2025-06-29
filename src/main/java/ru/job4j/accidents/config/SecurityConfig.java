package ru.job4j.accidents.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	PasswordEncoder passwordEncoder;

	DataSource dataSource;

	@Bean
	public UserDetailsService userDetailsService() {
		JdbcUserDetailsManager uds = new JdbcUserDetailsManager(dataSource);
		uds.setUsersByUsernameQuery(
				"select username, password, enabled from users where username = ?"
		);
		uds.setAuthoritiesByUsernameQuery(
				" select u.username, a.authority "
						+ "from authorities as a, users as u "
						+ "where u.username = ? and u.authority_id = a.id");
		return uds;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/login", "/reg").permitAll()
						.requestMatchers("/**").hasAnyRole("USER", "ADMIN")
				)
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/")
						.failureUrl("/login?error=true")
						.permitAll()
				)
				.logout(logout -> logout
						.logoutSuccessUrl("/login?logout=true")
						.invalidateHttpSession(true)
						.permitAll()
				);

		return http.build();
	}

}