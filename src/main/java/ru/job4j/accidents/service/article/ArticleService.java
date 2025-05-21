package ru.job4j.accidents.service.article;

import ru.job4j.accidents.model.Article;

import java.util.Collection;
import java.util.List;

public interface ArticleService {

	Article getArticleById(int id);

	Collection<Article> getAllArticles();

	Collection<Article> getArticleByAccidentId(int id);

	boolean deleteByAccidentId(int id);

	Collection<Article> getArticlesByAccidentId(List<Integer> articleIds);
}
