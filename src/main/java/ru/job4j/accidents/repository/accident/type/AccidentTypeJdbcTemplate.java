package ru.job4j.accidents.repository.accident.type;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class AccidentTypeJdbcTemplate {

	private final JdbcTemplate jdbc;

	public List<AccidentType> getAllAccidentTypes() {
		log.info("Getting all accident types");
		return jdbc.query("select id, name from accident_type",
				(resultSet, rowNum) -> {
					AccidentType newType = new AccidentType();
					newType.setId(resultSet.getInt("id"));
					newType.setName(resultSet.getString("name"));
					return newType;
				});
	}
}
