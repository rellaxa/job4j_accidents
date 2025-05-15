package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

	private final AccidentService accidentService;

	@GetMapping
	public String getAllAccidents(Model model) {
		model.addAttribute("user", "relaxa");
		model.addAttribute("accidents", accidentService.getAll());
		return "/accidents/all";
	}

	@GetMapping("/addAccident")
	public String viewCreateAccident(Model model) {
		model.addAttribute("user", "relaxa");
		model.addAttribute("types", accidentService.getAccidentTypes());
		return "/accidents/create";
	}

	@PostMapping("/saveAccident")
	public String createAccident(@ModelAttribute Accident accident) {
		accidentService.create(accident);
		return "redirect:/accidents";
	}

	@GetMapping("/oneAccident")
	public String getAccidentById(@RequestParam("id") int id, Model model) {
		var accidentOpt = accidentService.findById(id);
		if (accidentOpt.isEmpty()) {
			model.addAttribute("user", "relaxa");
			var error = String.format("Accident with id %d does not exist", id);
			model.addAttribute("error", error);
			return "/errors/404";
		}
		model.addAttribute("user", "relaxa");
		model.addAttribute("accident", accidentOpt.get());
		model.addAttribute("types", accidentService.getAccidentTypes());
		return "/accidents/one";
	}

	@PostMapping("/updateAccident")
	public String updatePage(@ModelAttribute Accident accident, Model model) {
		model.addAttribute("user", "relaxa");
		accidentService.update(accident);
		return "redirect:/accidents";
	}

}
