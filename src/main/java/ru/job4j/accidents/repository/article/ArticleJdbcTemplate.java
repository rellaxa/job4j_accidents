package ru.job4j.accidents.repository.article;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Article;

import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ArticleJdbcTemplate implements ArticleRepository {

	private JdbcTemplate jdbc;

	@Override
	public Article getById(int id) {
		log.info("Getting article by id: {}", id);
		return jdbc.queryForObject("select id, name from article where id=?",
				(resultSet, rowNum) -> {
					Article newArticle = new Article();
					newArticle.setId(resultSet.getInt("id"));
					newArticle.setName(resultSet.getString("name"));
					return newArticle;
				},
				id);
	}

	@Override
	public List<Article> getAll() {
		log.info("Getting all articles");
		return jdbc.query("select id, name from article",
				(resultSet, rowNum) -> {
					Article newArticle = new Article();
					newArticle.setId(resultSet.getInt("id"));
					newArticle.setName(resultSet.getString("name"));
					return newArticle;
				});
	}

	@Override
	public List<Article> getAllByAccidentId(int accidentId) {
		log.info("Getting all articles by accidentId: {}", accidentId);
		List<Integer> articleIds = jdbc.query("select article_id from accident_article where accident_id=?",
				(resultSet, rowNum) ->
						resultSet.getInt("article_id"), accidentId
		);
		return articleIds.stream()
				.map(this::getById)
				.toList();
	}

	@Override
	public boolean deleteByAccidentId(int accidentId) {
		log.info("Deleting articles by accident id: {}", accidentId);
		return jdbc.update(
				"delete from accident_article where accident_id=?",
				accidentId) > 0;
	}
}
