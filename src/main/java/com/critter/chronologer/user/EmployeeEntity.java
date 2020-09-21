package com.critter.chronologer.user;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;


@Entity
public class EmployeeEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    @ManyToMany(mappedBy = "employees")
    private List<ScheduleEntity> schedules;

    @ElementCollection()
    private Set<EmployeeSkill> skills;

    @ElementCollection
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

    public Set<DayOfWeek> getAvailableDays() {
        return daysAvailable;
    }

    public void setAvailableDaysForEmployee(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
