package ru.job4j.accidents.repository.accident;

import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentRepository {

	Accident add(Accident accident, List<Integer> articleIds);

	Accident getById(int id);

	List<Accident> getAll();

	void update(Accident accident, List<Integer> ids);

	boolean deleteById(int id);
}
