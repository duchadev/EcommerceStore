package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
  List<OrderDetail> findOrderDetailsByOrderOrderId(int orderId);
}
