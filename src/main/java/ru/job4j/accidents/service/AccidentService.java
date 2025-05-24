package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.accident.AccidentRepository;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class AccidentService {

	private final AccidentRepository accidentRepository;

	private final ArticleService articleService;

	public void create(Accident accident, String[] articleIds) {
		log.info("Creating accident {}", accident);
		var articles = articleService.getArticlesByAccidentId(articleIds);
		accident.setArticles(articles);
		accidentRepository.save(accident);
		log.info("Accident created: {}", accident);
	}

	public Optional<Accident> findById(int id) {
		log.info("Find accident by id: {}", id);
		return accidentRepository.findWithArticlesById(id);
	}

	public List<Accident> getAll() {
		log.info("Get all accidents");
		return accidentRepository.findAll();
	}

	public void update(Accident accident, String[] articleIds) {
		var articles = articleService.getArticlesByAccidentId(articleIds);
		accident.setArticles(articles);
		log.info("Updating accident {}", accident);
		accidentRepository.save(accident);
	}

	public boolean deleteById(int id) {
		log.info("Deleting accident by id: {}", id);
		return accidentRepository.deleteById(id) > 0;
	}
}
