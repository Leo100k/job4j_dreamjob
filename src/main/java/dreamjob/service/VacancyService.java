package dreamjob.service;
import dreamjob.model.Vacancy;
import dreamjob.dto.FileDto;

import java.util.Collection;
import java.util.Optional;

public interface VacancyService {

    Vacancy save(Vacancy vacancy, FileDto image);

    boolean deleteById(int id);

    boolean update(Vacancy vacancy, FileDto image);

    Optional<Vacancy> findById(int id);

    Collection<Vacancy> findAll();
}