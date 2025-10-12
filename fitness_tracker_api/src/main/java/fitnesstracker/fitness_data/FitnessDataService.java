package fitnesstracker.fitness_data;

import fitnesstracker.applications.ApplicationEntity;
import fitnesstracker.applications.ApplicationRepository;
import fitnesstracker.common.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessDataService {

    private final FitnessDataRepository fitnessDataRepository;
    private final ApplicationRepository applicationRepository;

    private final Mapper mapper;

    public FitnessDataService(FitnessDataRepository fitnessDataRepository,
                              ApplicationRepository applicationRepository, Mapper mapper) {
        this.fitnessDataRepository = fitnessDataRepository;
        this.applicationRepository = applicationRepository;
        this.mapper = mapper;
    }

    public void saveData(UploadDataRequest request, String appToken) {
        ApplicationEntity applicationEntity = applicationRepository.findByApikey(appToken);
        FitnessDataEntity fitnessDataEntity = mapper.toEntity(request);
        fitnessDataEntity.setApplication(applicationEntity);
        fitnessDataRepository.save(fitnessDataEntity);
    }

    public List<FitnessDataDto> getAllData() {
        return mapper.toDtoList(fitnessDataRepository.findAll());
    }
}
