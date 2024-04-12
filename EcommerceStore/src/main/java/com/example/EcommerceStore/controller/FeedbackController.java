package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Feedback;
import com.example.EcommerceStore.repository.FeedbackRepository;
import com.example.EcommerceStore.repository.ProductRepository;
import com.example.EcommerceStore.repository.UserRepository;
import com.example.EcommerceStore.service.ProductService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/EcommerceStore")
public class FeedbackController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private FeedbackRepository feedbackRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ProductService productService;

  @GetMapping("/do-feedback")
  public String reviewProducts(Model model, HttpSession session) {
    model.addAttribute("products", productRepository.findProductsToFeedbackByUser((int) session.getAttribute("user_id")));
    return "feedback";
  }

  @PostMapping("/submit-feedback")
  public String submitFeedback(@RequestParam("productId") int productId,
      @RequestParam("description") String description,
      @RequestParam("rate") int rate, HttpSession session) {

    Feedback feedback = new Feedback();
    feedback.setProduct(productRepository.findProductByProductId(productId));
    feedback.setDescription(description);
    feedback.setRating(rate);
    feedback.setUser(userRepository.findUserByUserId((int) session.getAttribute("user_id")));

    feedbackRepository.save(feedback);
    productService.setRating(productRepository.findProductByProductId(productId));
    return "redirect:/EcommerceStore/do-feedback";
  }
}
