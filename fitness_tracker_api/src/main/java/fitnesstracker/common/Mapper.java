package fitnesstracker.common;

import fitnesstracker.applications.ApplicationDto;
import fitnesstracker.fitness_data.FitnessDataDto;
import fitnesstracker.fitness_data.UploadDataRequest;
import fitnesstracker.applications.ApplicationEntity;
import fitnesstracker.fitness_data.FitnessDataEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class Mapper {

    public FitnessDataEntity toEntity(UploadDataRequest request) {
        return new FitnessDataEntity(
                request.activity(),
                request.username(),
                request.duration(),
                request.calories()
        );
    }

    public List<FitnessDataDto> toDtoList(List<FitnessDataEntity> entities) {
        return entities.stream()
                .sorted(Comparator.comparing(FitnessDataEntity::getCreatedOn).reversed())
                .map(this::toDto)
                .toList();
    }

    FitnessDataDto toDto(FitnessDataEntity entity) {
        ApplicationEntity applicationEntity = entity.getApplication();
        return new FitnessDataDto(
                entity.getId(),
                entity.getUsername(),
                entity.getActivity(),
                entity.getDuration(),
                entity.getCalories(),
                applicationEntity != null ? applicationEntity.getName() : "Unknown"
        );
    }

    public ApplicationDto toDto(ApplicationEntity app) {
        return new ApplicationDto(
                app.getId(),
                app.getName(),
                app.getDescription(),
                app.getApikey(),
                app.getCategory());
    }
}
