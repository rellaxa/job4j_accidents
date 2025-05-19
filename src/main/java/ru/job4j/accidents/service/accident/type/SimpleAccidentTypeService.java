package ru.job4j.accidents.service.accident.type;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.accident.type.AccidentTypeRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

	private final AccidentTypeRepository accidentTypeRepository;

	@Override
	public Collection<AccidentType> getAllAccidentTypes() {
		return accidentTypeRepository.getAllAccidentTypes();
	}
}
