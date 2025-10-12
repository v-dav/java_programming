package fitnesstracker.fitness_data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FitnessDataRepository extends JpaRepository<FitnessDataEntity, UUID> {
}
