package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class RegControlTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private PasswordEncoder encoder;

	@MockitoBean
	private UserRepository users;

	@MockitoBean
	private AuthorityRepository authorities;

	@Test
	public void getRegPage() throws Exception {
		this.mockMvc.perform(get("/reg"))
				.andDo(print())
				.andExpect(view().name("/reg"));
	}

	@Test
	public void whenRegisterNewUser() throws Exception {
		var authority = new Authority();
		when(encoder.encode("123")).thenReturn("123");
		when(authorities.findByAuthority(anyString())).thenReturn(authority);
		this.mockMvc.perform(post("/reg")
				.param("username", "relaxa")
				.param("password", "123")
						.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(users).save(userCaptor.capture());
		assertThat(userCaptor.getValue().getUsername()).isEqualTo("relaxa");
		assertThat(userCaptor.getValue().getPassword()).isEqualTo("123");
		assertThat(userCaptor.getValue().getAuthority()).isEqualTo(authority);
	}

}