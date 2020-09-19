package com.critter.chronologer.repository;

import com.critter.chronologer.user.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
