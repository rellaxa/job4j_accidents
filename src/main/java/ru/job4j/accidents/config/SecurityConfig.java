package ru.job4j.accidents.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
		if (!uds.userExists("user")) {
			UserDetails user = User.withUsername("user")
					.password(passwordEncoder.encode("111"))
					.roles("USER")
					.build();
			uds.createUser(user);
		}
		if (!uds.userExists("admin")) {
			UserDetails admin = User.withUsername("admin")
					.password(passwordEncoder.encode("111"))
					.roles("USER", "ADMIN")
					.build();
			uds.createUser(admin);
		}
		return uds;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/login").permitAll()
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