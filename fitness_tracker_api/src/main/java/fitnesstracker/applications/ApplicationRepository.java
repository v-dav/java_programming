package fitnesstracker.applications;

import fitnesstracker.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, UUID> {
    List<ApplicationEntity> findByUserOrderByCreatedOnDesc(UserEntity userEntity);
    boolean existsByName(String name);
    boolean existsByApikey(String apikey);
    ApplicationEntity findByApikey(String apikey);
}