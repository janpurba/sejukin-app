package com.sejukin.backend.repository;

import com.sejukin.backend.model.UserServiceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserServiceTemplateRepository extends JpaRepository<UserServiceTemplate, Long> {
    List<UserServiceTemplate> findByUserId(Long userId);
    Optional<UserServiceTemplate> findByUserIdAndServiceTypeId(Long userId, Long serviceTypeId);
}
