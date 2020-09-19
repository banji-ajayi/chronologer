package com.critter.chronologer.user;

import com.critter.chronologer.pet.PetEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinTable(name = "schedule_meeting", joinColumns = @JoinColumn(name = "schedule_meeting_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<EmployeeSkill> meetingActivities;

    @JoinTable(name = "schedule_employee", joinColumns = @JoinColumn(name = "schedule_emp_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @ManyToMany()
    private List<EmployeeEntity> employees;

    @JoinTable(name = "schedule_pet", joinColumns = @JoinColumn(name = "schedule_pet_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @ManyToMany()
    private List<PetEntity> pets;

    public ScheduleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getMeetingActivities() {
        return meetingActivities;
    }

    public void setMeetingActivities(Set<EmployeeSkill> meetingActivities) {
        this.meetingActivities = meetingActivities;
    }

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }

    public List<PetEntity> getPets() {
        return pets;
    }

    public void setPets(List<PetEntity> pets) {
        this.pets = pets;
    }
}
