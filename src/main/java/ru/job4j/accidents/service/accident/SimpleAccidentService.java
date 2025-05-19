package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Article;
import ru.job4j.accidents.repository.accident.AccidentRepository;
import ru.job4j.accidents.service.article.ArticleService;
import ru.job4j.accidents.service.accident.type.AccidentTypeService;

import java.util.*;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

	private final AccidentRepository accidentRepository;

	private final AccidentTypeService accidentTypeService;

	private final ArticleService articleService;

	public void create(Accident accident, String[] articleIds) {
		accidentRepository.add(getAccidentWithArticles(accident, articleIds));
	}

	public Optional<Accident> findById(int id) {
		var accident = accidentRepository.getById(id);
		if (accident == null) {
			return Optional.empty();
		}
		accident.setArticles(new HashSet<>(articleService.getArticleByAccidentId(id)));
		return Optional.of(accident);
	}

	public List<Accident> getAll() {
		return accidentRepository.getAll();
	}

	public void update(Accident accident, String[] articleIds) {
		accidentRepository.update(getAccidentWithArticles(accident, articleIds));
	}

	@Override
	public boolean deleteById(int id) {
		var deleted = articleService.deleteByAccidentId(id);
		return deleted && accidentRepository.deleteById(id);
	}

	private Accident getAccidentWithArticles(Accident accident, String[] articleIds) {
		Set<Article> articles = new HashSet<>();
		for (String articleId : articleIds) {
			articles.add(articleService.getArticleById(Integer.parseInt(articleId)));
		}
		accident.setArticles(articles);
		return accident;
	}
}
