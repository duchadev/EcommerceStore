package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.Schedule;
import com.example.EcommerceStore.entity.Staff;
import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {

  public Staff scheduling(Date date, int shift) ;
}
