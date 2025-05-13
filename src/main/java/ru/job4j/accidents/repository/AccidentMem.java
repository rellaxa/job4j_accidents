package ru.job4j.accidents.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
public class AccidentMem {

	private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

	private final AtomicInteger counter = new AtomicInteger(0);

	public void add(Accident accident) {
		accident.setId(counter.getAndIncrement());
		log.info("Adding accident: {}", accident);
		accidents.put(accident.getId(), accident);
	}

	public Optional<Accident> getById(int id) {
		log.info("Getting accident: {}", id);
		return Optional.ofNullable(accidents.get(id));
	}

	public List<Accident> getAll() {
		log.info("Getting all accidents");
		return new ArrayList<>(accidents.values());
	}

	public List<Accident> generateAccidents(int size) {
		log.info("Generating accidents: {}", size);
		List<Accident> generatedAccidents = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int id = counter.getAndIncrement();
			Accident accident = new Accident(id, String.format("Accident #%d", id), "text", "address");
			accidents.put(id, accident);
			generatedAccidents.add(accident);
		}
		return generatedAccidents;
	}

	public void deleteAll() {
		log.info("Deleting all accidents");
		accidents.clear();
	}
}
