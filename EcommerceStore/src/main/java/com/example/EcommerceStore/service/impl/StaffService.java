package com.example.EcommerceStore.service.impl;

import com.example.EcommerceStore.entity.Staff;
import com.example.EcommerceStore.repository.StaffRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
  @Autowired
  private StaffRepository repository;

  public List<Staff> listAll() {
    return (List<Staff>) repository.findAll(Sort.by(Sort.Direction.DESC,"staff_id"));
  }

  public void save(Staff staff){
    repository.save(staff);
  }

  public Staff get(int id)  {
    Optional<Staff> result = repository.findById(id);
    if (result.isPresent()){
      return result.get();
    }else
    {
      return null;
    }

  }
  public void delete(int id)  {
    Optional<Staff> result = repository.findById(id);
    if (result.isPresent()){
      repository.deleteById(id);
    }

  }
}
