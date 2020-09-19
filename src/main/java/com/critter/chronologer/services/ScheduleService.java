package com.critter.chronologer.services;

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

    public ScheduleEntity saveSchedule(ScheduleEntity schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleEntity> findAllSchedules() {
        return scheduleRepository.findAll();
    }

}
