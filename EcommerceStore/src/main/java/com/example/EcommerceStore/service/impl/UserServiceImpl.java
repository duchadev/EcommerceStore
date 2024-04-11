package com.example.EcommerceStore.service.impl;

import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.entity.UserAddress;
import com.example.EcommerceStore.repository.UserAddressRepository;
import com.example.EcommerceStore.repository.UserRepository;
import com.example.EcommerceStore.request.RegisterRequest;
import com.example.EcommerceStore.response.RegisterResponse;
import com.example.EcommerceStore.service.UserService;
import java.sql.Date;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;
  private final EmailService emailService;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  UserAddressRepository userAddressRepository;

  @Override
  public boolean register(RegisterRequest registerRequest) {
    User existingUser = userRepository.findByUserEmail(registerRequest.getEmail().trim());
    if (existingUser != null) {
      return true;
    } else {
      LocalDate currentDate = LocalDate.now();
      User users = User.builder()
          .user_name("")
          .userEmail(registerRequest.getEmail())
          .password(passwordEncoder.encode(registerRequest.getPassword()))
          .roles("USER")
          .user_phoneNumber("")
          .birthday(Date.valueOf(currentDate))
          .otpGeneratedTime(LocalDateTime.now())
          .verified(0)
          .build();
      String otp = generateOTP();
      users.setOtp(otp);

      User savedUser = userRepository.save(users);
      sendVerificationEmail(savedUser.getUserEmail(), otp);
      return false;
    }


  }

  //resend otp for verify
  public void send(String email) {
    User user = userRepository.findUserByUserEmail(email);

    String otp = generateOTP();
    user.setOtp(otp);
    userRepository.save(user);
    sendVerificationEmail(email, otp);
  }
  //re_verify account


  //verify for create new user
  @Override
  public boolean verify(String email, String otp) {
    User user = userRepository.findUserByUserEmail(email);

    System.out.println("Email nhap vao: " + email);
    System.out.println("OTP nhapp vao" + otp);
    System.out.println("OTP trong db:" + user.getOtp());

    // Convert emails to lowercase for case-insensitive comparison
    if (user.getVerified() == 1) {
      return false;
    } else {
      String lowerCaseEmail = email.toLowerCase();
      if (otp.trim().equals(user.getOtp().trim()) && lowerCaseEmail.equals(
          user.getUserEmail().toLowerCase())) {
        user.setVerified(1);
        user.setOtp("0");
        userRepository.save(user);
        return true;
      } else {
        return false;
      }
    }

  }

  //verify for change pass
  @Override
  public boolean verifyForgotPass(String email, String otp) {
    User user = userRepository.findUserByUserEmail(email);
    String lowerCaseEmail = email.toLowerCase();
    if (otp.trim().equals(user.getOtp().trim()) && lowerCaseEmail.equals(
        user.getUserEmail().toLowerCase())) {
      user.setOtp("0");
      userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void changePass(String current_password, String new_password, String re_new_password,
      int user_id, Model model, String message_err1, String message_err2) {
    User user = userRepository.findUserByUserId(user_id);
    if (passwordEncoder.matches(current_password.trim(), user.getPassword().trim())) {

      if (new_password.trim().equalsIgnoreCase(re_new_password.trim())) {
        user.setPassword(passwordEncoder.encode(new_password));
        userRepository.save(user);
      } else {
        model.addAttribute("re_new_pass_error", message_err1);
      }
    } else {
      System.out.println(current_password);
      System.out.println(passwordEncoder.encode(current_password));
      System.out.println(user.getPassword());
      model.addAttribute("cur_pass_error", message_err2);
    }
  }

  // change new password
  @Override
  public boolean changeNewPass(String email, String new_pass, String confirm_new_pass) {
    User user = userRepository.findUserByUserEmail(email);
    if (new_pass.trim().equalsIgnoreCase(confirm_new_pass.trim())) {

      user.setPassword(passwordEncoder.encode(new_pass));
      userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }


  private String generateOTP() {
    Random random = new Random();
    int otpValue = 100000 + random.nextInt(900000);
    return String.valueOf(otpValue);
  }

  private void sendVerificationEmail(String email, String otp) {
    String subject = "Email verification";
    String body = "your verification otp is: " + otp;
    emailService.sendEmail(email, subject, body);
  }

  public void sendNotification(String email, String productName) {
    String subject = "Thông báo sản phẩm mới";
    String body = "Sản phẩm " + productName + " đã có hàng.";
    emailService.sendEmail(email, subject, body);
  }

  public void sendScheduleConfirmedEmail(String email, String customer_name,
      String customer_phone, java.util.Date date, String time, String note) {
    String subject = "Thông báo thông tin đặt lịch vệ sinh";
    String body = "Tên khách hàng: " + customer_name + "<br>"
        + "Số điện thoại: " + customer_phone + "<br>"
        + "Ngày giờ: " + date + " - " + time + "<br>"
        + "Ghi chú: " + note;
    emailService.sendEmail(email, subject, body);
  }

  public void sendSchedulePcBookingEmail(
      String email, int user_address, java.util.Date date, String time, String note
  ) {
    String subject = "Thông báo thông tin đặt lịch vệ sinh";
    String body = "Tên khách hàng: " + userAddressRepository.getUserAddressByAddressId(user_address)
        .getReceive_name() + "<br>"
        + "Số điện thoại: " + userAddressRepository.getUserAddressByAddressId(user_address)
        .getReceive_phone() + "<br>"
        + "Địa chỉ khách hàng: " + userAddressRepository.getUserAddressByAddressId(user_address)
        + "<br>"
        + "Ngày giờ: " + date + " - " + time + "<br>"
        + "Ghi chú: " + note;
    emailService.sendEmail(email, subject, body);
  }
}