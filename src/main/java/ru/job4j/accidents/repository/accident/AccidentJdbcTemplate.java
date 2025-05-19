package ru.job4j.accidents.repository.accident;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Article;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

	private final JdbcTemplate jdbc;

	@Override
	public Accident add(Accident accident) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO accident(name, text, address, accident_type_id) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
			);
			ps.setString(1, accident.getName());
			ps.setString(2, accident.getText());
			ps.setString(3, accident.getAddress());
			ps.setInt(4, accident.getType().getId());
			return ps;
		}, keyHolder);
		var number = (Number) keyHolder.getKeys().get("id");
		int id = number.intValue();
		accident.setId(id);
		log.info("Added accident: {}", accident);
		for (Article article : accident.getArticles()) {
			jdbc.update(
					"insert into accident_article(accident_id, article_id) values (?, ?)",
					id,
					article.getId());
		}
		return accident;
	}

	@Override
	public Accident getById(int id) {
		log.info("Getting accident by id: {}", id);
		Accident accident;
		try {
			accident = jdbc.queryForObject(
					"""
							select name,
							       text,
							       address,
							       accident_type_id
							from accident
							where id = ?
						""",
					(resultSet, rowNum) -> {
						Accident newAccident = new Accident();
						newAccident.setId(id);
						newAccident.setName(resultSet.getString("name"));
						newAccident.setText(resultSet.getString("text"));
						newAccident.setAddress(resultSet.getString("address"));
						var accidentType = getByAccidentTypeId(resultSet.getInt("accident_type_id"));
						newAccident.setType(accidentType);
						return newAccident;
					},
					id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return accident;
	}

	@Override
	public List<Accident> getAll() {
		log.info("Getting all accidents");
		return jdbc.query(
				"select id, name, text, address from accident",
				(resultSet, rowNum) -> {
					Accident newAccident = new Accident();
					newAccident.setId(resultSet.getInt("id"));
					newAccident.setName(resultSet.getString("name"));
					newAccident.setText(resultSet.getString("text"));
					newAccident.setAddress(resultSet.getString("address"));
					return newAccident;
				});
	}

	@Override
	public void update(Accident accident) {
		log.info("Updating accident: {}", accident);
		jdbc.update("""
							update accident
							set name = ?,
							    text = ?,
							    address = ?,
								accident_type_id = ?
							where id = ?
						""",
				accident.getName(),
				accident.getText(),
				accident.getAddress(),
				accident.getType().getId(),
				accident.getId()
		);
		var articles = accident.getArticles();
		log.info("Updated articles: {}", articles);
		jdbc.update(
				"delete from accident_article where accident_id = ?",
				accident.getId()
		);
		for (Article article : accident.getArticles()) {
			jdbc.update(
					"insert into accident_article(accident_id, article_id) values (?, ?)",
					accident.getId(),
					article.getId());
		}
	}

	@Override
	public boolean deleteById(int id) {
		return jdbc.update("delete from accident where id = ?", id) > 0;
	}

	private AccidentType getByAccidentTypeId(int id) {
		log.info("Getting accident type by id: {}", id);
		return jdbc.queryForObject(
				"select name from accident_type where id = ?",
				(resultSet, rowNum) -> {
					AccidentType newType = new AccidentType();
					newType.setId(id);
					newType.setName(resultSet.getString("name"));
					return newType;
				},
				id);
	}
}
