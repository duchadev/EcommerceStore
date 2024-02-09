package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Order;
import com.example.EcommerceStore.entity.UserAddress;
import com.example.EcommerceStore.repository.OrderRepository;
import com.example.EcommerceStore.repository.UserAddressRepository;
import com.example.EcommerceStore.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/EcommerceStore")
public class OrderTrackingController {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserAddressRepository userAddressRepository;
@GetMapping("/orderTracking")
public String getOrderTracking(HttpServletRequest request, Model model)
{
  int user_id = Integer.parseInt(request.getParameter("user_id")) ;
  String user_email = userRepository.findUserByUserId(user_id).getUserEmail();
  String status = request.getParameter("status");
  List<Order> orderList = orderRepository.findOrdersByUserIdAndStatus(user_id,status);
  model.addAttribute("user_email", user_email);
  model.addAttribute("orderList", orderList);
  model.addAttribute("userAddressRepository", userAddressRepository);
  return "orderTracking";
}
}
