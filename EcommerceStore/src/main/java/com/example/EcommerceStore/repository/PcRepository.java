package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.PC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcRepository extends JpaRepository<PC,Integer> {


}
