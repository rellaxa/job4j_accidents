package ru.job4j.accidents.service.article;

import ru.job4j.accidents.model.Article;

import java.util.Collection;

public interface ArticleService {

	Article getArticleById(int id);

	Collection<Article> getAllArticles();

	Collection<Article> getArticleByAccidentId(int id);

	boolean deleteByAccidentId(int id);
}
