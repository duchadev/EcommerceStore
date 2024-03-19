package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Order;
import com.example.EcommerceStore.entity.OrderDetail;
import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.UserAddress;
import com.example.EcommerceStore.repository.OrderDetailRepository;
import com.example.EcommerceStore.repository.OrderRepository;
import com.example.EcommerceStore.repository.UserAddressRepository;
import com.example.EcommerceStore.repository.UserRepository;
import com.example.EcommerceStore.service.OrderServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;

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
  @Autowired
  private OrderServiceImpl orderService;

  @GetMapping("/orderTracking")
  public String getOrderTracking(HttpServletRequest request, Model model, HttpSession session
  ) {
    int user_id = Integer.parseInt(request.getParameter("user_id"));
    String user_email = userRepository.findUserByUserId(user_id).getUserEmail();
    String status = request.getParameter("status");
    session.setAttribute("user_id", user_id);
    session.setAttribute("status", status);

    List<Order> orderList = orderService.getInitOrder(user_id, status);

    model.addAttribute("user_id", user_id);
    model.addAttribute("status", status);
    model.addAttribute("user_email", user_email);
    model.addAttribute("orderList", orderList);

    model.addAttribute("userAddressRepository", userAddressRepository);
    return "orderTracking";
  }

  @GetMapping("/orderTracking/more")
  public String getMoreProduct(HttpServletRequest request, Model model, @RequestParam int page,
      @RequestParam int size, HttpSession session) {
    String status = request.getParameter("status");
    int user_id = Integer.parseInt(request.getParameter("user_id"));
//    System.out.println(status);
//    System.out.println(user_id);
    session.setAttribute("user_id", user_id);
    session.setAttribute("status", status);
    List<Order> morePendingOrder = orderService.getMoreOrder(status, user_id, page, size);
    for (Order o : morePendingOrder) {
      System.out.println(o.toString());
    }
    model.addAttribute("userAddressRepository", userAddressRepository);
    model.addAttribute("moreOrder", morePendingOrder);
    return "orderTracking";
  }

  @GetMapping("/successOrder")
  public String getSuccessOrder(HttpServletRequest request, Model model, HttpSession session) {
    int user_id = Integer.parseInt(request.getParameter("user_id"));
    String status = request.getParameter("status");
    String user_email = userRepository.findUserByUserId(user_id).getUserEmail();
    session.setAttribute("user_id", user_id);
    session.setAttribute("status", status);
    List<Order> successOrder = orderService.getInitOrder(user_id, status);
    model.addAttribute("user_id", user_id);
    model.addAttribute("status", status);
    model.addAttribute("user_email", user_email);
    model.addAttribute("orderList", successOrder);
    model.addAttribute("userAddressRepository", userAddressRepository);
    return "orderTracking";

  }

  @GetMapping("/successOrder/more")
  public String getMoreOrder(HttpServletRequest request, Model model, @RequestParam int page,
      @RequestParam int size, HttpSession session) {
    String status = request.getParameter("status");
    int user_id = Integer.parseInt(request.getParameter("user_id"));
//    System.out.println(status);
//    System.out.println(user_id);
    session.setAttribute("user_id", user_id);
    session.setAttribute("status", status);
    List<Order> moreSuccessOrder = orderService.getMoreOrder(status, user_id, page, size);
    for (Order o : moreSuccessOrder) {
      System.out.println(o.toString());
    }
    model.addAttribute("userAddressRepository", userAddressRepository);
    model.addAttribute("moreOrder", moreSuccessOrder);
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


}
