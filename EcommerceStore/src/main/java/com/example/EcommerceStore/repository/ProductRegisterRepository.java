package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.ProductRegister;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRegisterRepository extends JpaRepository<ProductRegister, Integer> {
ProductRegister findProductRegisterByProductId(int product_id);
List<ProductRegister> findProductRegistersByProductId(int product_id);
}
