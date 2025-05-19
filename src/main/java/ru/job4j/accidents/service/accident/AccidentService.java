package ru.job4j.accidents.service.accident;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentService {

	void create(Accident accident, String[] articleIds);

	Optional<Accident> findById(int id);

	List<Accident> getAll();

	void update(Accident accident, String[] articleIds);

	boolean deleteById(int id);
}
