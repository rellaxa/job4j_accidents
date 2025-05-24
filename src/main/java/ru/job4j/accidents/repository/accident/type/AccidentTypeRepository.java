package ru.job4j.accidents.repository.accident.type;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;


public interface AccidentTypeRepository extends CrudRepository<AccidentType, Long> {

	@Override
	Collection<AccidentType> findAll();
}
