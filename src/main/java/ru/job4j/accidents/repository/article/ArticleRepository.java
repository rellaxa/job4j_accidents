package ru.job4j.accidents.repository.article;

import ru.job4j.accidents.model.Article;

import java.util.List;

public interface ArticleRepository {

	Article getById(int id);

	List<Article> getAll();

	List<Article> getAllByAccidentId(int accidentId);

	boolean deleteByAccidentId(int id);
}
