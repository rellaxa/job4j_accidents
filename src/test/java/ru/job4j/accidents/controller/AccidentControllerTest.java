package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccidentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AccidentService accidentService;

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

	@Test
	@WithMockUser
	public void whereCreateAccidentThanRedirect() throws Exception {
		this.mockMvc.perform(post("/accidents/saveAccident")
						.param("name", "test accident")
						.param("text", "description")
						.param("address", "address")
						.param("rIds", "1", "2", "3")
						.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
		ArgumentCaptor<Accident> accidentArg = ArgumentCaptor.forClass(Accident.class);
		ArgumentCaptor<String[]> arrayArg = ArgumentCaptor.forClass(String[].class);
		verify(accidentService).create(accidentArg.capture(), arrayArg.capture());
		Accident resultAccident = accidentArg.getValue();
		String[] array = arrayArg.getValue();
		assertThat(resultAccident.getName()).isEqualTo("test accident");
		assertThat(resultAccident.getText()).isEqualTo("description");
		assertThat(resultAccident.getAddress()).isEqualTo("address");
		assertThat(array).isEqualTo(new String[]{"1", "2", "3"});
	}

	@Test
	@WithMockUser
	public void whenUpdateAccidentThenRedirect() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", "test accident");
		params.add("text", "description");
		params.add("address", "address");
		params.add("rIds", "1");
		this.mockMvc.perform(post("/accidents/updateAccident")
						.params(params)
						.with(csrf()))
				.andDo(print())
				.andExpect(status().is3xxRedirection());
		ArgumentCaptor<Accident> accidentArg = ArgumentCaptor.forClass(Accident.class);
		verify(accidentService).update(accidentArg.capture(), any(String[].class));
		Accident resultAccident = accidentArg.getValue();
		assertThat(resultAccident.getName()).isEqualTo("test accident");
		assertThat(resultAccident.getText()).isEqualTo("description");
		assertThat(resultAccident.getAddress()).isEqualTo("address");
	}

}