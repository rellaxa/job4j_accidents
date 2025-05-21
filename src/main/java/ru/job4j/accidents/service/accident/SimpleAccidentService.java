package ru.job4j.accidents.service.accident;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.accident.AccidentRepository;
import ru.job4j.accidents.service.article.ArticleService;
import ru.job4j.accidents.service.accident.type.AccidentTypeService;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

	private final AccidentRepository accidentRepository;

	private final AccidentTypeService accidentTypeService;

	private final ArticleService articleService;

	public void create(Accident accident, String[] articleIds) {
		var ids = Arrays.stream(articleIds).map(Integer::parseInt).toList();
		accidentRepository.add(accident, ids);
		log.info("Accident created: {}", accident);
	}

	public Optional<Accident> findById(int id) {
		var accident = accidentRepository.getById(id);
		if (accident == null) {
			return Optional.empty();
		}
		accident.setArticles(new HashSet<>(articleService.getArticleByAccidentId(id)));
		return Optional.of(accident);
	}

	public Collection<Accident> getAll() {
		return accidentRepository.getAll();
	}

	public void update(Accident accident, String[] articleIds) {
		var ids = Arrays.stream(articleIds).map(Integer::parseInt).toList();
		accidentRepository.update(accident, ids);
	}

	@Override
	public boolean deleteById(int id) {
		var deleted = articleService.deleteByAccidentId(id);
		return deleted && accidentRepository.deleteById(id);
	}
}
