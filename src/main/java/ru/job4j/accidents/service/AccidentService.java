package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Article;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {

	private final AccidentMem accidentMem;

	private final ArticleService articleService;

	public void create(Accident accident, String[] articleIds) {
		accidentMem.add(getAccidentWithArticles(accident, articleIds));
	}

	public Optional<Accident> findById(int id) {
		return accidentMem.getById(id);
	}

	public List<Accident> getAll() {
		return accidentMem.getAll();
	}

	public void update(Accident accident, String[] articleIds) {
		accidentMem.update(getAccidentWithArticles(accident, articleIds));
	}

	public void deleteAll() {
		accidentMem.deleteAll();
	}

	private Accident getAccidentWithArticles(Accident accident, String[] articleIds) {
		Set<Article> articles = new HashSet<>();
		for (String articleId : articleIds) {
			articles.add(articleService.findById(Integer.parseInt(articleId)));
		}
		accident.setArticles(articles);
		return accident;
	}
}
