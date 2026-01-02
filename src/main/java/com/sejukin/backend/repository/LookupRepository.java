package com.sejukin.backend.repository;

import com.sejukin.backend.model.Lookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, Long> {
    Optional<Lookup> findByCode(String code);
    List<Lookup> findByType(String type);
}
