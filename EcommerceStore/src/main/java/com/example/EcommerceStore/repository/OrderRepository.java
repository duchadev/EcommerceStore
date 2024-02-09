package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
List<Order> findOrdersByUserIdAndStatus(int user_id, String status);

}
