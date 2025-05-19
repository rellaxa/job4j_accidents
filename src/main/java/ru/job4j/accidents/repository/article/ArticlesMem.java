package ru.job4j.accidents.repository.article;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.accidents.model.Article;

import java.util.*;

@Slf4j
public class ArticlesMem {

	private final Map<Integer, Article> articles = new HashMap<>();

	public ArticlesMem() {
		articles.put(1, new Article(1, "Article 1"));
		articles.put(2, new Article(2, "Article 2"));
		articles.put(3, new Article(3, "Article 3"));
	}

	public Article getArticleById(int id) {
		log.info("Getting article by id: {} ", id);
		return articles.get(id);
	}

	public List<Article> getArticles() {
		log.info("Getting all articles");
		return new ArrayList<>(articles.values());
	}
}
