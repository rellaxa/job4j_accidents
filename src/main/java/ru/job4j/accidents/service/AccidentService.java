package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

	private final AccidentMem accidentMem;

	public void create(Accident accident) {
		accidentMem.add(accident);
	}

	public Optional<Accident> findById(int id) {
		return accidentMem.getById(id);
	}

	public List<Accident> getAll() {
		return accidentMem.getAll();
	}

	public List<AccidentType> getAccidentTypes() {
		return List.of(
				new AccidentType(1, "Две машины"),
				new AccidentType(2, "Машина и человек"),
				new AccidentType(3, "Машина и велосипед")
		);
	}

	public void update(Accident accident) {
		accidentMem.update(accident);
	}

	public void deleteAll() {
		accidentMem.deleteAll();
	}
}
