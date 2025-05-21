package ru.job4j.accidents.repository.accident.type;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.List;

public interface AccidentTypeRepository {

	List<AccidentType> getAllAccidentTypes();
}
