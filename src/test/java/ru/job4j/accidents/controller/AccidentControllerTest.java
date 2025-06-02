package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccidentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	public void getCreatedAccidentPage() throws Exception {
		this.mockMvc.perform(get("/accidents/addAccident"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("types"))
				.andExpect(model().attributeExists("articles"))
				.andExpect(view().name("/accidents/create"));
	}

	@Test
	@WithMockUser
	public void attemptGetAccidentWithInvalidIdThenErrorPage() throws Exception {
		this.mockMvc.perform(get("/accidents/oneAccident")
						.param("id", "100"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("error"))
				.andExpect(view().name("/errors/404"));
	}

	@Test
	@WithMockUser
	public void getAllAccidents() throws Exception {
		this.mockMvc.perform(get("/accidents"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("accidents"))
				.andExpect(view().name("/accidents/all"));
	}

	@Test
	@WithMockUser
	public void whenDeleteAccidentWithInvalidIdThenErrorPage() throws Exception {
		this.mockMvc.perform(get("/accidents/deleteAccident/{id}", 1))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(model().attributeExists("error"))
				.andExpect(view().name("/errors/404"));
	}
}