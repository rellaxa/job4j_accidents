package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;


@Controller
@AllArgsConstructor
@RequestMapping("/reg")
public class RegControl {

	private final PasswordEncoder encoder;

	private final UserRepository users;

	private final AuthorityRepository authorities;

	@GetMapping
	public String getRegPage() {
		return "/reg";
	}


	@PostMapping
	public String regSave(@ModelAttribute User user) {
		user.setEnabled(true);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setAuthority(authorities.findByAuthority("ROLE_USER"));
		users.save(user);
		return "redirect:/login";
	}
}
