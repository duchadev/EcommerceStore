package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.Order;
import java.util.List;

public interface OrderService {
List<Order> getInitOrder(int user_id, String status);


  List<Order> getMoreOrder(String status, int user_id, int page, int size);
}
