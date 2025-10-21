package org.vdav.web_quiz_engine.completion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vdav.web_quiz_engine.user.UserEntity;

@Repository
public interface CompletionRepository extends JpaRepository<CompletionEntity, Integer> {
    Page<CompletionEntity> findByUser(UserEntity user, Pageable pageable);
}
