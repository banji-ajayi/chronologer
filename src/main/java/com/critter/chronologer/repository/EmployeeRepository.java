package com.critter.chronologer.repository;

import com.critter.chronologer.user.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
  List<EmployeeEntity> getAllByDaysAvailable(DayOfWeek dayOfWeek);
}


