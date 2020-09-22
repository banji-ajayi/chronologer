package com.critter.chronologer.repository;

import com.critter.chronologer.pet.PetEntity;
import com.critter.chronologer.user.EmployeeEntity;
import com.critter.chronologer.user.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> getAllByEmployeesContains(EmployeeEntity employee);

    List<ScheduleEntity> getAllByPetsContains(PetEntity pet);

    List<ScheduleEntity> getAllByPetsIn(List<PetEntity> pets);
}
