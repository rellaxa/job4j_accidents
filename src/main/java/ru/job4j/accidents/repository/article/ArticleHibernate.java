package ru.job4j.accidents.repository.article;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Article;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ArticleHibernate implements ArticleRepository {

	private final SessionFactory sf;

	@Override
	public Article getById(int id) {
		return null;
	}

	@Override
	public List<Article> getAll() {
		try (Session session = sf.openSession()) {
			return session
					.createQuery("from Article", Article.class)
					.list();
		}
	}

	@Override
	public List<Article> getAllByAccidentId(int accidentId) {
		try (Session session = sf.openSession()) {
			List<Integer> articleIds = session.createNativeQuery(
							"SELECT article_id FROM accident_article WHERE accident_id = :accId")
					.setParameter("accId", accidentId)
					.getResultList();
			return getArticlesByArticleIds(articleIds);
		}
	}

	@Override
	public List<Article> getArticlesByArticleIds(List<Integer> articleIds) {
		try (Session session = sf.openSession()) {
			var query = session.createQuery("from Article a where a.id in :fIds", Article.class);
			query.setParameter("fIds", articleIds);
			return query.list();
		}
	}

	@Override
	public boolean deleteByAccidentId(int accidentId) {
		log.info("Deleting article by accident id: {}", accidentId);
		try (Session session = sf.openSession()) {
			Transaction th = session.beginTransaction();
			var deleted = session.createNativeQuery("delete from accident_article where accident_id = :fId")
					.setParameter("fId", accidentId)
					.executeUpdate() > 0;
			th.commit();
			return deleted;
		}
	}

}
