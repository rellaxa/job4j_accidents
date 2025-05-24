package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Article;
import ru.job4j.accidents.repository.article.ArticleRepository;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;

	public Collection<Article> getAllArticles() {
		log.info("Getting all articles");
		return articleRepository.findAll();
	}

	public List<Article> getArticlesByAccidentId(String[] accidentIds) {
		var ids = Arrays.stream(accidentIds).map(Integer::parseInt).toList();
		return (List<Article>) articleRepository.findAllById(ids);
	}
}
