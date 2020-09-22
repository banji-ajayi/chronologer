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

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> meetingActivities;

    @ManyToMany(targetEntity = EmployeeEntity.class)
    private List<EmployeeEntity> employees;

    @JoinTable(name = "schedule_pet", joinColumns = @JoinColumn(name = "schedule_pet_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @ManyToMany(targetEntity = PetEntity.class)
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
