package com.critter.chronologer.schedule;

import com.critter.chronologer.pet.PetEntity;
import com.critter.chronologer.services.CustomerService;
import com.critter.chronologer.services.EmployeeService;
import com.critter.chronologer.services.PetService;
import com.critter.chronologer.services.ScheduleService;
import com.critter.chronologer.user.EmployeeEntity;
import com.critter.chronologer.user.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping

    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setMeetingActivities(scheduleDTO.getActivities());
        return getScheduleDTO(scheduleService.saveSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAllSchedules()
                .stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findAllSchedulesForPet(petId)
                .stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findAllSchedulesForEmployee(employeeId)
                .stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findAllSchedulesForCustomer(customerId)
                .stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    private ScheduleDTO getScheduleDTO(ScheduleEntity schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(EmployeeEntity::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(PetEntity::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getMeetingActivities());
        return scheduleDTO;
    }
}