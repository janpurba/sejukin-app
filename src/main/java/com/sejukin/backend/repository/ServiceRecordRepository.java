package com.sejukin.backend.repository;

import com.sejukin.backend.model.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    @Query("select  o from ServiceRecord o " +
            "left join fetch o.customer where o.nextReminderDate =:date " +
            " and o.reminderStatus =:status")
    List<ServiceRecord> findByNextReminderDateAndReminderStatus(LocalDate date, String status);
}