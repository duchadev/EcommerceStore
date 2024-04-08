package com.example.EcommerceStore.controller;


import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.repository.UserRepository;

import com.example.EcommerceStore.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;


@Controller
@RequestMapping("/EcommerceStore")
public class EditProfileController {

  @Autowired
  public PasswordEncoder bCryptPasswordEncoder;
  @Autowired
  public UserRepository userRepository;
  @Autowired
  UserServiceImpl userService;

  @GetMapping("/profile/{user_email}")
  public String profile(@PathVariable String user_email, Model model, HttpSession session) {
    Optional<User> optionalUser = userRepository.findByEmail(user_email);

    if (optionalUser.isPresent()) {
      User user = optionalUser.get();

      model.addAttribute("user", user);

      session.setAttribute("user", user);
    }

    return "profile_test";
  }

  //update profile
  @Transactional
  @PostMapping("/profile/edit")

  public String editProfile(@RequestParam("user_id") int user_id,
      @RequestParam("user_name") String user_name,
      @RequestParam("user_phoneNumber") String user_phoneNumber,
      @RequestParam("birthday") String birthdayString,

      @RequestParam("user_email") String user_email,
      Model model) {
    try {
      Date birthday = null;
      try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(birthdayString);
        birthday = new Timestamp(parsedDate.getTime());
      } catch (ParseException e) {

        e.printStackTrace();
      }

      userRepository.updateUserByUserId(user_id, user_name, user_phoneNumber, birthday);

      return "redirect:" + UriComponentsBuilder.fromPath("/EcommerceStore/profile/{user_email}")
          .buildAndExpand(user_email)
          .toUriString(); // redirect to the profile page after successful update
    } catch (Exception ex) {
      model.addAttribute("error", ex.getMessage());
      ex.printStackTrace();
      return "error";
    }
  }

  // change password
  @GetMapping("/profile/change-pass/{user_id}")
  public String changePassword(@PathVariable("user_id") int user_id, Model model) {
    User user = userRepository.findUserByUserId(user_id);
    model.addAttribute("user", user);
    return "change_pass";
  }

  @PostMapping("/profile/change-pass")
  public String changePass(@RequestParam("currentpassword") String current_password,
      @RequestParam("newpassword") String new_password,
      @RequestParam("renewpassword") String re_new_password,
      @RequestParam("user_id") int user_id, Model model) {
    User user = userRepository.findUserByUserId(user_id);
    String message_err2 = "Mật khẩu hiện tại không đúng";
    String message_err1 = "Mật khẩu không khớp";
    userService.changePass(current_password, new_password, re_new_password, user_id, model,
        message_err1, message_err2);
    model.addAttribute("current_password", current_password);
    model.addAttribute("new_password", new_password);
    model.addAttribute("user", user);

    return "change_pass";
  }
}
