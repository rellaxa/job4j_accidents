package ru.job4j.accidents.repository.accident;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Article;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class AccidentHibernate {

	private final SessionFactory sf;

	public Accident add(Accident accident, List<Integer> articleIds) {
		try (Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			setAccidentWithArticles(accident, articleIds, session);
			session.persist(accident);
			tx.commit();
			log.info("Accident saved: {}", accident);
			return accident;
		}
	}

	public Accident getById(int id) {
		try (Session session = sf.openSession()) {
			return session.get(Accident.class, id);
		}
	}

	public List<Accident> getAll() {
		try (Session session = sf.openSession()) {
			return session.createQuery("from Accident", Accident.class)
					.list();
		}
	}

	public void update(Accident accident, List<Integer> articleIds) {
		log.info("Updating accident: {} with article ids: {}", accident, articleIds);
		try (Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			setAccidentWithArticles(accident, articleIds, session);
			session.merge(accident);
			tx.commit();
		}
	}

	public boolean deleteById(int id) {
		log.info("Deleting accident by id: {}", id);
		try (Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			var deleted = session.createQuery("delete from Accident where id = :fId")
					.setParameter("fId", id)
					.executeUpdate() > 0;
			tx.commit();
			return deleted;
		}
	}

	private void setAccidentWithArticles(Accident accident, List<Integer> articleIds, Session session) {
		var query = session.createQuery(
				"from Article a where a.id in :fIds", Article.class);
		query.setParameter("fIds", articleIds);
		var articles = query.getResultList();
		log.info("Articles by article ids: {}", articles);
		accident.setArticles(new ArrayList<>(articles));
	}
}
