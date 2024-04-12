package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AdminController {
  @Autowired
  private UserRepository userRepository;
  @GetMapping("/admin")
  public String adminHome(ModelMap Model, HttpSession session){
    User admin = userRepository.findUserByUserId((int)session.getAttribute("user_id"));
    if (admin.getRoles().trim().equals("ADMIN")){
      session.setAttribute("admin",true);

      return "adminHome";
    }
    return "redirect:/EcommerceStore/product";
  }
}
