package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.ProductRegister;
import com.example.EcommerceStore.repository.ProductRegisterRepository;
import com.example.EcommerceStore.repository.ProductRepository;
import com.example.EcommerceStore.service.impl.EmailService;
import com.example.EcommerceStore.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/EcommerceStore")
public class RegisterProductController {

  @Autowired
  UserServiceImpl userService;
  @Autowired
  ProductRegisterRepository productRegisterRepository;
  @Autowired
  ProductRepository productRepository;

  @PostMapping("/register-product")
  public String registerProduct(HttpServletRequest request) {
    String user_email = request.getParameter("user_email");
    int product_id = Integer.parseInt(request.getParameter("product_id"));
    ProductRegister productRegister = new ProductRegister(product_id, user_email);
    productRegisterRepository.save(productRegister);
    return "register_product_success";
  }

  @GetMapping("/create")
  public String create() {
    return "test";
  }

  @PostMapping("/createProduct")
  public String createProduct(HttpServletRequest request) {
    // update product
    int product_id = 3;
    String productName = "Zenbook";
    int product_quantity = 20;
    String product_image = "";
    int rating = 5;
    String product_brand = "Asus";
    int product_price = 23000000;
    String product_type = "Laptop";

    Product product = new Product(product_id, productName, product_quantity, product_image, rating,
        product_brand,
        product_price, product_type);
//    System.out.println("product: " + product.getProduct_quantity());
    Product p = productRepository.getProductByProductId(product_id);
//    System.out.println("p: " + p.getProduct_quantity());
    List<ProductRegister> productRegisterList = productRegisterRepository.findProductRegistersByProductId(
        product_id);
    //if present product quantity = 0 then send notification for registered user
    if (product_quantity - p.getProduct_quantity() == product_quantity) {
      int id = product.getProductId();
      for (ProductRegister productRegister : productRegisterList) {
        String email = productRegister.getUser_email();
        userService.sendNotification(email, productName);
      }
//      System.out.println("sent");
    }
    productRepository.save(product);
    return "success";
  }
}
