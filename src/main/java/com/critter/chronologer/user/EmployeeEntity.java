package com.critter.chronologer.user;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;


@Entity
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    @ManyToMany(mappedBy = "employees")
    private List<ScheduleEntity> schedules;

    @ElementCollection
    @JoinTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Column(nullable = false)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @JoinTable(name = "employee_day", joinColumns = @JoinColumn(name = "employee_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<DayOfWeek> daysAvailable;

    public EmployeeEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScheduleEntity> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleEntity> schedules) {
        this.schedules = schedules;
    }

    public Set<EmployeeSkill> getEmployeeSkills() {
        return skills;
    }

    public void setEmployeeSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getAvailableDaysForEmployee() {
        return daysAvailable;
    }

    public void setAvailableDaysForEmployee(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
