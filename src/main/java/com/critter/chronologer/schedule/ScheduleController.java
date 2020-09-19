package com.critter.chronologer.schedule;

import com.critter.chronologer.pet.PetEntity;
import com.critter.chronologer.services.CustomerService;
import com.critter.chronologer.services.EmployeeService;
import com.critter.chronologer.services.PetService;
import com.critter.chronologer.services.ScheduleService;
import com.critter.chronologer.user.EmployeeEntity;
import com.critter.chronologer.user.ScheduleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping

    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<EmployeeEntity> employees = employeeService.findByIds(scheduleDTO.getEmployeeIds());
        List<PetEntity> pets = petService.findAllPetsById(scheduleDTO.getPetIds());

        ScheduleEntity schedule = new ScheduleEntity();

        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setPets(pets);
        schedule.setEmployees(employees);

        ScheduleEntity savedSchedule = scheduleService.saveSchedule(schedule);

        employees.stream().forEach(employee -> {
            if (employee.getSchedules() == null) employee.setSchedules(new ArrayList<>());
            employee.getSchedules().add(savedSchedule);
        });

        pets.stream().forEach(pet -> {
            if (pet.getSchedules() == null) pet.setSchedules(new ArrayList<>());
            pet.getSchedules().add(savedSchedule);
        });
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleEntity> schedules = scheduleService.findAllSchedules();

        return entityToDto(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return entityToDto(petService.findPetById(petId).getSchedules());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        EmployeeEntity employee = employeeService.findEmployeeById(employeeId);
        if (employee.getSchedules() == null)
            return null;

        List<ScheduleEntity> schedules = employee.getSchedules();
        return entityToDto(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<PetEntity> pets = customerService.findCustomerById(customerId).getPets();
        HashMap<Long, ScheduleEntity> map = new HashMap<>();

        pets.stream().forEach(pet -> {
            pet.getSchedules().stream()
                    .forEach(schedule -> { map.put(schedule.getId(), schedule);
            });
        });
        return entityToDto(new ArrayList<>(map.values()));
    }

    private List<ScheduleDTO> entityToDto(List<ScheduleEntity> schedules) {
        return schedules.stream().map(schedule -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDTO);

            scheduleDTO.setEmployeeIds(schedule.getEmployees()
                    .stream()
                    .map(employee -> employee.getId()).collect(Collectors.toList()));
            scheduleDTO.setPetIds(schedule.getPets()
                    .stream()
                    .map(pet -> pet.getId()).collect(Collectors.toList()));

            return scheduleDTO;

        }).collect(Collectors.toList());
    }

}