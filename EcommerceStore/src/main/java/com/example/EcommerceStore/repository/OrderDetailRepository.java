package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.Order;
import com.example.EcommerceStore.entity.OrderDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
  List<OrderDetail> findOrderDetailsByOrderOrderId(Long orderId);
  List<OrderDetail> findByOrder(Optional<Order> order);
}
