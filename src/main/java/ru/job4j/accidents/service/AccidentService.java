package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
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

	public List<Accident> generate(int size) {
		return accidentMem.generateAccidents(size);
	}

	public void deleteAll() {
		accidentMem.deleteAll();
	}

	public void update(Accident accident) {
		accidentMem.update(accident);
	}
}
