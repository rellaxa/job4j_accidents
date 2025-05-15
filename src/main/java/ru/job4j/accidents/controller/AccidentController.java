package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

	private final AccidentService accidentService;

	@GetMapping
	public String getAllAccidents(Model model) {
		model.addAttribute("accidents", accidentService.getAll());
		model.addAttribute("user", "relaxa");
		return "/accidents/allAccidents";
	}

	@GetMapping("/addAccident")
	public String viewCreateAccident(Model model) {
		model.addAttribute("user", "relaxa");
		return "/accidents/createAccident";
	}

	@PostMapping("/saveAccident")
	public String save(@ModelAttribute Accident accident) {
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
		model.addAttribute("accident", accidentOpt.get());
		model.addAttribute("user", "relaxa");
		return "/accidents/oneAccident";
	}

	@PostMapping("/updateAccident")
	public String updatePage(@ModelAttribute Accident accident, Model model) {
		model.addAttribute("user", "relaxa");
		accidentService.update(accident);
		return "redirect:/accidents";
	}

}
