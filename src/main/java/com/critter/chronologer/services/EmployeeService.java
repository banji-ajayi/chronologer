package com.critter.chronologer.services;


import com.critter.chronologer.exception.NotFoundException;
import com.critter.chronologer.pet.PetEntity;
import com.critter.chronologer.repository.EmployeeRepository;
import com.critter.chronologer.user.CustomerEntity;
import com.critter.chronologer.user.EmployeeEntity;
import com.critter.chronologer.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeEntity findEmployeeById(Long empId) {
        return employeeRepository.findById(empId).orElseThrow(() ->
                new NotFoundException("Fmployee for : " + empId + " is Not Found"));
    }

    public EmployeeEntity save(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    public List<EmployeeEntity> findByIds(List<Long> employeeIds) {
        return employeeRepository.findAllById(employeeIds);
    }

    public List<EmployeeEntity> findEmployeesBasedOnAvailability(Set<EmployeeSkill> employeeSkills,
                                                                 LocalDate date){
        List<EmployeeEntity> employeesReturned = employeeRepository.getAllByDaysAvailable(date.getDayOfWeek()).stream()
                .filter(emp -> emp.getEmployeeSkills().containsAll(employeeSkills))
                .collect(Collectors.toList());
        return employeesReturned;

    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        EmployeeEntity employee = employeeRepository.getOne(employeeId);
        employee.setAvailableDaysForEmployee(daysAvailable);
        employeeRepository.save(employee);
    }

}
