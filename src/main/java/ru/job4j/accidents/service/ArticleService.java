package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Article;
import ru.job4j.accidents.repository.ArticlesMem;

import java.util.Collection;

@Service
@AllArgsConstructor
public class ArticleService {

	private final ArticlesMem articlesMem;

	public Article findById(int id) {
		return articlesMem.getArticleById(id);
	}

	public Collection<Article> getArticles() {
		return articlesMem.getArticles();
	}

}
