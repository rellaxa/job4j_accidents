package ru.job4j.accidents.repository.article;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Article;

import java.util.Collection;


public interface ArticleRepository extends CrudRepository<Article, Integer> {

	@Override
	Collection<Article> findAll();
}
