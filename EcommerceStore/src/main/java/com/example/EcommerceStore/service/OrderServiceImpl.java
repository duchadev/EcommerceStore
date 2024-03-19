package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.Order;
import com.example.EcommerceStore.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public List<Order> getInitOrder(int user_id, String status) {
    Page<Order> searchPage = orderRepository.findOrdersByUserIdAndStatus(user_id, status,
        PageRequest.of(0, 4));
    return searchPage.getContent();
  }

  @Override
  public List<Order> getMoreOrder(String status, int user_id, int page, int size) {
    // Perform the search using pagination
    Page<Order> searchPage = orderRepository.findOrdersByUserIdAndStatus(user_id, status,
        PageRequest.of(page, size));

    return searchPage.getContent();
  }


}
