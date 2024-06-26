package dreamjob.service;
import org.springframework.stereotype.Service;
import dreamjob.model.City;
import dreamjob.repository.CityRepository;

import java.util.Collection;

@Service
public class SimpleCityService implements CityService {

    private final CityRepository cityRepository;

    public SimpleCityService(CityRepository sql2oCityRepository) {
                this.cityRepository = sql2oCityRepository;
    }

    @Override
    public Collection<City> findAll() {
        return cityRepository.findAll();
    }
}