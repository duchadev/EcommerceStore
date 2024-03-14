package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.Schedule;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
List<Schedule> findSchedulesByDate(Date date);
}
