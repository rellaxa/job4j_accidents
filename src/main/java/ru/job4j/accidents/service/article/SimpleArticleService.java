package ru.job4j.accidents.service.article;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Article;
import ru.job4j.accidents.repository.article.ArticleRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleArticleService implements ArticleService {

	private final ArticleRepository articleRepository;

	@Override
	public Article getArticleById(int id) {
		return articleRepository.getById(id);
	}

	@Override
	public Collection<Article> getAllArticles() {
		return articleRepository.getAll();
	}

	@Override
	public Collection<Article> getArticleByAccidentId(int accidentId) {
		return articleRepository.getAllByAccidentId(accidentId);
	}

	@Override
	public boolean deleteByAccidentId(int id) {
		return articleRepository.deleteByAccidentId(id);
	}
}
