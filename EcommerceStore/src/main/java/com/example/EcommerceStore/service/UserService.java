package com.example.EcommerceStore.service;


import com.example.EcommerceStore.request.RegisterRequest;
import com.example.EcommerceStore.response.RegisterResponse;
import org.springframework.ui.Model;

public interface UserService {

  boolean register(RegisterRequest registerRequest);

  boolean verify(String email,String otp);
  void changePass(String current_password, String new_password, String re_new_password,
      int user_id, Model model, String message_err1, String message_err2);
  void send(String email);
  boolean verifyForgotPass(String email, String otp) ;
  public boolean changeNewPass(String email, String new_pass, String confirm_new_pass) ;
}
