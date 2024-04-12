package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.Order;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
List<Order> findOrdersByUserIdAndStatus(int user_id, String status);
  Page<Order> findOrdersByUserIdAndStatus(int user_id, String status, PageRequest of);
List<Order> findOrdersByUserId(int user_id);
  List<Order> findOrdersByStatus(String pending);
}
