package dreamjob.service;
import dreamjob.dto.FileDto;
import dreamjob.model.Vacancy;
import dreamjob.repository.VacancyRepository;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
@ThreadSafe
@Service
public class SimpleVacancyService implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final FileService fileService;

    private SimpleVacancyService(VacancyRepository vacancyRepository, FileService fileService) {
        this.vacancyRepository = vacancyRepository;
        this.fileService = fileService;
    }

    private void saveNewFile(Vacancy vacancy, FileDto image) {
        var file = fileService.save(image);
        vacancy.setFileId(file.getId());
    }

    @Override
    public Vacancy save(Vacancy vacancy, FileDto image) {
        saveNewFile(vacancy, image);
        return vacancyRepository.save(vacancy);
    }

    @Override
    public boolean deleteById(int id) {
        boolean result;
        var fileOptional = findById(id);
        if (fileOptional.isPresent()) {
            result = vacancyRepository.deleteById(id);
            fileService.deleteById(fileOptional.get().getFileId());
            } else {
            result = vacancyRepository.deleteById(id);
        }
        return result;
    }

    @Override
    public boolean update(Vacancy vacancy, FileDto image) {

        var isNewFileEmpty = image.getContent().length == 0;
        if (isNewFileEmpty) {
            return vacancyRepository.update(vacancy);
        }
        /* если передан новый не пустой файл, то старый удаляем, а новый сохраняем */
        var oldFileId = vacancy.getFileId();
        saveNewFile(vacancy, image);
        var isUpdated = vacancyRepository.update(vacancy);
        fileService.deleteById(oldFileId);
        return isUpdated;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }
}