package ru.job4j.accidents.repository.accident.type;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

@AllArgsConstructor
public class AccidentTypeHibernate {

	private final SessionFactory sf;

	public List<AccidentType> getAllAccidentTypes() {
		try (Session session = sf.openSession()) {
			return session
					.createQuery("from AccidentType", AccidentType.class)
					.list();
		}
	}

}
