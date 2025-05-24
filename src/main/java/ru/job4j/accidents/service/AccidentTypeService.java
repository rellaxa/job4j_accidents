package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.accident.type.AccidentTypeRepository;

import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
public class AccidentTypeService {

	private final AccidentTypeRepository accidentTypeRepository;

	public Collection<AccidentType> getAllAccidentTypes() {
		log.info("Getting all accident types");
		return accidentTypeRepository.findAll();
	}

}
