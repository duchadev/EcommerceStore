package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Order;
import com.example.EcommerceStore.entity.OrderDetail;
import com.example.EcommerceStore.entity.UserAddress;
import com.example.EcommerceStore.repository.OrderDetailRepository;
import com.example.EcommerceStore.repository.OrderRepository;
import com.example.EcommerceStore.repository.UserAddressRepository;
import com.example.EcommerceStore.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.text.DecimalFormat;
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
  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @GetMapping("/orderTracking")
  public String getOrderTracking(HttpServletRequest request, Model model, HttpSession session) {
    int user_id = Integer.parseInt(request.getParameter("user_id"));
    String user_email = userRepository.findUserByUserId(user_id).getUserEmail();
    String status = request.getParameter("status");
    session.setAttribute("user_id", user_id);
    session.setAttribute("status", status);
    List<Order> orderList = orderRepository.findOrdersByUserIdAndStatus(user_id, status);
    model.addAttribute("user_email", user_email);
    model.addAttribute("orderList", orderList);
    model.addAttribute("userAddressRepository", userAddressRepository);
    return "orderTracking";
  }

  @GetMapping("/viewOrderDetail")
  public String viewOrderDetail(HttpServletRequest request, Model model, HttpSession session) {
    int order_id = Integer.parseInt(request.getParameter("order_id"));
    List<OrderDetail> orderDetailList = orderDetailRepository.findOrderDetailsByOrderOrderId(
        order_id);
    model.addAttribute("orderDetailList", orderDetailList);
    int user_id = (int) session.getAttribute("user_id");
    String status = String.valueOf(session.getAttribute("status"));
    model.addAttribute("user_id", user_id);

    model.addAttribute("status", status);
    return "detail";
  }
  public String formatNumber(float number)
  {
    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    return decimalFormat.format(number);

  }
}
