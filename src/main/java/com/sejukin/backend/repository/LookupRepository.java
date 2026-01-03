package com.sejukin.backend.repository;

import com.sejukin.backend.model.Lookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, Long> {
    Optional<Lookup> findByCode(String code);
    List<Lookup> findByType(String type);

    @Query("SELECT l FROM Lookup l WHERE l.type = :type AND l.status.code = 'ACTIVE'")
    List<Lookup> findByTypeAndStatusActive(String type, String statusType);
}
