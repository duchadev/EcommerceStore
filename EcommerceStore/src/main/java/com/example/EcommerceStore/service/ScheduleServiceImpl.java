package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.ProductStaff;
import com.example.EcommerceStore.entity.Schedule;
import com.example.EcommerceStore.repository.ProductStaffRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{
@Autowired
private ProductStaffRepository productStaffRepository;
  @Override
  public boolean getAvailableTime( String time, List<Schedule> scheduleList) {
    // count variable represent for num of user booking that time in a day
    List<ProductStaff> productStaffs = productStaffRepository.findAll();
    int count=0;
    for(Schedule schedule: scheduleList)
    {
      if( (schedule.getTime().trim()).equalsIgnoreCase(time.trim()))
      {
        count++;
      }
    }
    if(count < productStaffs.size())
    {
      return true;
    } else
    {
      return false;
    }

  }
}
