package ru.job4j.accidents.repository.accident;

import lombok.extern.slf4j.Slf4j;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AccidentMem implements AccidentRepository {

	private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

	private final AtomicInteger counter = new AtomicInteger(1);

	@Override
	public Accident add(Accident accident, List<Integer> articleIds) {
		accident.setId(counter.getAndIncrement());
		log.info("Adding accident: {}", accident);
		accidents.put(accident.getId(), accident);
		return accident;
	}

	@Override
	public Accident getById(int id) {
		log.info("Getting accident: {}", id);
		return accidents.get(id);
	}

	@Override
	public List<Accident> getAll() {
		log.info("Getting all accidents");
		return new ArrayList<>(accidents.values());
	}

	@Override
	public void update(Accident accident, List<Integer> ids) {
		accidents.put(accident.getId(), accident);
	}

	@Override
	public boolean deleteById(int id) {
		return accidents.remove(id) != null;
	}

	public void deleteAll() {
		log.info("Deleting all accidents");
		accidents.clear();
	}
}
