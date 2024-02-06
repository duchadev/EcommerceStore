package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Cart;
import com.example.EcommerceStore.entity.CartItem;
import com.example.EcommerceStore.entity.Order;
import com.example.EcommerceStore.entity.OrderDetail;
import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.UserAddress;
import com.example.EcommerceStore.repository.CartItemRepository;
import com.example.EcommerceStore.repository.CartRepository;
import com.example.EcommerceStore.repository.OrderDetailRepository;
import com.example.EcommerceStore.repository.OrderRepository;
import com.example.EcommerceStore.repository.ProductRepository;
import com.example.EcommerceStore.repository.UserAddressRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/EcommerceStore")
public class OrderController {

  @Autowired
  private UserAddressRepository userAddressRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private OrderDetailRepository orderDetailRepository;
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private CartRepository cartRepository;

  @GetMapping("/order")
  public String viewOrder(@RequestParam("user_id") int user_id,
      @RequestParam("total") int total, Model model) {

    List<UserAddress> userAddressList = userAddressRepository.findUserAddressesByUserId(user_id);
    model.addAttribute("userAddressList", userAddressList);
    model.addAttribute("total", total);
    model.addAttribute("user_id", user_id);
    return "order";
  }

  @PostMapping("/order")
  public String confirmOrder(@RequestParam("user_id") int user_id,
      @RequestParam("useraddress") int address_id,
      @RequestParam("payment_method") int payment_method, @RequestParam("total") int total,
      HttpSession session) {
    Cart cart = cartRepository.findCartByUserId(user_id);
    if (payment_method == 1) {

      String status = "NOT YET";
      Order order = new Order(address_id, total, status, user_id);
      orderRepository.save(order);
      List<CartItem> cartItemList = (List<CartItem>) session.getAttribute("cartItemList");
      for (CartItem cartItem : cartItemList) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct_id(cartItem.getProductId());
        orderDetail.setQuantity(cartItem.getQuantity());
        orderDetail.setProduct_name(
            productRepository.getProductByProductId(cartItem.getProductId()).getProduct_name());
        orderDetail.setImage(
            productRepository.getProductByProductId(cartItem.getProductId()).getProduct_image());
        orderDetailRepository.save(orderDetail);
        cartItemRepository.delete(cartItem);

      }
      session.removeAttribute("cart");
      session.removeAttribute("cartItemList");
      cartRepository.delete(cart);
      return "success";
    } else {
      return "vnpay";
    }

  }
}
