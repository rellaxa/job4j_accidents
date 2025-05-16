package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Article;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.ArticleService;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

	private final AccidentService accidentService;

	private final ArticleService articleService;

	@GetMapping
	public String getAllAccidents(Model model) {
		model.addAttribute("user", "relaxa");
		model.addAttribute("accidents", accidentService.getAll());
		return "/accidents/all";
	}

	@GetMapping("/addAccident")
	public String createAccidentPage(Model model) {
		model.addAttribute("user", "relaxa");
		model.addAttribute("types", getAccidentTypes());
		model.addAttribute("articles", articleService.getArticles());
		return "/accidents/create";
	}

	@PostMapping("/saveAccident")
	public String createAccident(@ModelAttribute Accident accident, HttpServletRequest req) {
		String[] ids = req.getParameterValues("rIds");
		System.out.println(Arrays.toString(ids));
		accidentService.create(accident, ids);
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
		model.addAttribute("types", getAccidentTypes());
		model.addAttribute("allArticles", articleService.getArticles());
		return "/accidents/one";
	}

	@PostMapping("/updateAccident")
	public String updatePage(@ModelAttribute Accident accident, HttpServletRequest req, Model model) {
		model.addAttribute("user", "relaxa");
		String[] ids = req.getParameterValues("rIds");
		accidentService.update(accident, ids);
		return "redirect:/accidents";
	}

	private List<AccidentType> getAccidentTypes() {
		return List.of(
				new AccidentType(1, "Две машины"),
				new AccidentType(2, "Машина и человек"),
				new AccidentType(3, "Машина и велосипед")
		);
	}

}
