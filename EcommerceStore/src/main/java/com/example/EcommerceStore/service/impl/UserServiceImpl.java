package com.example.EcommerceStore.service.impl;

import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.entity.UserAddress;
import com.example.EcommerceStore.repository.UserAddressRepository;
import com.example.EcommerceStore.repository.UserRepository;
import com.example.EcommerceStore.request.RegisterRequest;
import com.example.EcommerceStore.response.RegisterResponse;
import com.example.EcommerceStore.service.UserService;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    User existingUser = userRepository.findByUserEmail(registerRequest.getEmail());
    if (existingUser != null) {
      if (existingUser.isVerified()) {
        return false;
      }
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
          .build();
      String otp = generateOTP();
      users.setOtp(otp);

      User savedUser = userRepository.save(users);
      sendVerificationEmail(savedUser.getUserEmail(), otp);
    }


    return true;
  }


  @Override
  public void verify(String email, String otp) {
    User users = userRepository.findByUserEmail(email);
    if (users == null){
      throw new RuntimeException("User not found");
    } else if (users.isVerified()) {
      throw new RuntimeException("User is already verified");
    } else if (otp.equals(users.getOtp())) {
      users.setVerified(true);
      userRepository.save(users);
    }else {
      throw new RuntimeException("Internal Server error");
    }
  }


  private String generateOTP(){
    Random random = new Random();
    int otpValue = 100000 + random.nextInt(900000);
    return String.valueOf(otpValue);
  }

  private void sendVerificationEmail(String email,String otp){
    String subject = "Email verification";
    String body ="your verification otp is: "+otp;
    emailService.sendEmail(email,subject,body);
  }
  public void sendNotification(String email,String productName){
    String subject = "Thông báo sản phẩm mới";
    String body ="Sản phẩm "+productName+ " đã có hàng.";
    emailService.sendEmail(email,subject,body);
  }
  public void sendScheduleConfirmedEmail(String email,String customer_name,
      String customer_phone, java.util.Date date, String time,String note)
  {
      String subject = "Thông báo thông tin đặt lịch vệ sinh";
      String body = "Tên khách hàng: "+ customer_name + "<br>"
          +"Số điện thoại: " + customer_phone +"<br>"
          +"Ngày giờ: "+ date +" - "+ time +"<br>"
          + "Ghi chú: "+ note;
      emailService.sendEmail(email,subject,body);
  }
  public void sendSchedulePcBookingEmail(
    String email,  int user_address, java.util.Date date, String time, String note
  )
  {
    String subject = "Thông báo thông tin đặt lịch vệ sinh";
    String body = "Tên khách hàng: "+ userAddressRepository.getUserAddressByAddressId(user_address).getReceive_name() + "<br>"
        +"Số điện thoại: " + userAddressRepository.getUserAddressByAddressId(user_address).getReceive_phone() +"<br>"
       +"Địa chỉ khách hàng: "+ userAddressRepository.getUserAddressByAddressId(user_address) +"<br>"
        +"Ngày giờ: "+ date +" - "+ time +"<br>"
        + "Ghi chú: "+ note;
    emailService.sendEmail(email,subject,body);
  }
}