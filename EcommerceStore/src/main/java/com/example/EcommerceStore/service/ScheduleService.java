package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.Schedule;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {
boolean getAvailableTime(String time, List<Schedule> scheduleList );
}
