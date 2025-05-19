package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.accident.AccidentService;
import ru.job4j.accidents.service.accident.type.AccidentTypeService;
import ru.job4j.accidents.service.article.ArticleService;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

	private final AccidentService accidentService;

	private final ArticleService articleService;

	private final AccidentTypeService accidentTypeService;

	@GetMapping("/addAccident")
	public String createAccidentPage(Model model) {
		model.addAttribute("user", "relaxa");
		model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
		model.addAttribute("articles", articleService.getAllArticles());
		return "/accidents/create";
	}

	@PostMapping("/saveAccident")
	public String createAccident(@ModelAttribute Accident accident, HttpServletRequest req) {
		String[] ids = req.getParameterValues("rIds");
		accidentService.create(accident, ids);
		return "redirect:/accidents";
	}

	@GetMapping("/oneAccident")
	public String getAccidentById(@RequestParam("id") int id, Model model) {
		var accidentOpt = accidentService.findById(id);
		model.addAttribute("user", "relaxa");
		if (accidentOpt.isEmpty()) {
			var error = String.format("Accident with id %d does not exist", id);
			model.addAttribute("error", error);
			return "/errors/404";
		}
		model.addAttribute("accident", accidentOpt.get());
		model.addAttribute("types", accidentTypeService.getAllAccidentTypes());
		model.addAttribute("allArticles", articleService.getAllArticles());
		return "/accidents/one";
	}

	@GetMapping
	public String getAllAccidents(Model model) {
		model.addAttribute("user", "relaxa");
		model.addAttribute("accidents", accidentService.getAll());
		return "/accidents/all";
	}

	@PostMapping("/updateAccident")
	public String updatePage(@ModelAttribute Accident accident, HttpServletRequest req, Model model) {
		model.addAttribute("user", "relaxa");
		String[] ids = req.getParameterValues("rIds");
		accidentService.update(accident, ids);
		return "redirect:/accidents";
	}

	@GetMapping("/deleteAccident/{id}")
	public String deleteAccident(@PathVariable int id, Model model) {
		var deleted = accidentService.deleteById(id);
		model.addAttribute("user", "relaxa");
		if (!deleted) {
			var error = String.format("Accident with id %d does not exist", id);
			model.addAttribute("error", error);
			return "/errors/404";
		}
		return "redirect:/accidents";
	}

}
