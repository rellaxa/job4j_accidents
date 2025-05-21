package ru.job4j.accidents.repository.accident.type;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate implements AccidentTypeRepository {

	private final SessionFactory sf;

	@Override
	public List<AccidentType> getAllAccidentTypes() {
		try (Session session = sf.openSession()) {
			return session
					.createQuery("from AccidentType", AccidentType.class)
					.list();
		}
	}

}
