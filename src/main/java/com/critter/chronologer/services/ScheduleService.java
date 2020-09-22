package com.critter.chronologer.services;

import com.critter.chronologer.repository.CustomerRepository;
import com.critter.chronologer.repository.EmployeeRepository;
import com.critter.chronologer.repository.PetRepository;
import com.critter.chronologer.repository.ScheduleRepository;

import com.critter.chronologer.user.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private  PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ScheduleEntity saveSchedule(ScheduleEntity schedule, List<Long> employeeIds, List<Long> petIds) {
        schedule.setEmployees(employeeRepository.findAllById(employeeIds));
        schedule.setPets(petRepository.findAllById(petIds));
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleEntity> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleEntity> findAllSchedulesForPet(long petId) {
        return scheduleRepository.getAllByPetsContains(petRepository.getOne(petId));
    }

    public List<ScheduleEntity> findAllSchedulesForEmployee(long employeeId) {
        return scheduleRepository.getAllByEmployeesContains(employeeRepository.getOne(employeeId));
    }

    public List<ScheduleEntity> findAllSchedulesForCustomer(long customerId) {
        return scheduleRepository.getAllByPetsIn(customerRepository.getOne(customerId).getPets());
    }


}
