package ru.job4j.accidents.repository.accident;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;


public interface AccidentRepository extends CrudRepository<Accident, Integer> {

	@Query("select a from Accident a join fetch a.articles where a.id = ?1")
	Optional<Accident> findWithArticlesById(int id);

	@Override
	List<Accident> findAll();

	@Modifying
	@Query("delete from Accident a where a.id = :id")
	int deleteById(@Param("id") int id);

}
