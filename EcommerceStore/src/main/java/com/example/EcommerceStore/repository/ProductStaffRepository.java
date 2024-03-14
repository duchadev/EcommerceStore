package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.ProductStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStaffRepository extends JpaRepository<ProductStaff,Integer> {

}
