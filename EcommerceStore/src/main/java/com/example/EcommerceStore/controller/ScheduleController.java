package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Schedule;
import com.example.EcommerceStore.entity.UserAddress;
import com.example.EcommerceStore.repository.ScheduleRepository;
import com.example.EcommerceStore.repository.UserAddressRepository;
import com.example.EcommerceStore.repository.UserRepository;
import com.example.EcommerceStore.service.ScheduleServiceImpl;
import com.example.EcommerceStore.service.UserService;
import com.example.EcommerceStore.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/EcommerceStore/clean-booking")
public class ScheduleController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserAddressRepository userAddressRepository;
  @Autowired
  private ScheduleRepository scheduleRepository;
  @Autowired
  private ScheduleServiceImpl scheduleService;
@Autowired
private UserServiceImpl userService;
  @GetMapping("/booking")
  public String getMapping(HttpSession session, Model model) {
    String user_id = String.valueOf(session.getAttribute("user_id"));
    model.addAttribute("user_id", user_id);
    return "clean_booking";
  }

  @GetMapping("/clean-laptop-booking")
  public String cleanLaptopBooking(HttpSession session, Model model) {

    LocalDateTime currentDate = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = currentDate.format(formatter);
    System.out.println(date);
    model.addAttribute("current_date", date);
    int user_id = (int) (session.getAttribute("user_id"));
    model.addAttribute("user_id", user_id);
    model.addAttribute("product_type", "Laptop");

    return "clean_schedule";
  }

  @GetMapping("/clean-macbook-booking")
  public String cleanMacbookBooking(HttpSession session, Model model) {
    LocalDateTime currentDate = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = currentDate.format(formatter);
    model.addAttribute("current_date", date);
    int user_id = (int) (session.getAttribute("user_id"));
    model.addAttribute("user_id", user_id);
    model.addAttribute("product_type", "Macbook");
    return "clean_schedule";
  }

  @GetMapping("/clean-pc-booking")
  public String cleanPcBooking(HttpSession session, Model model) {
    int user_id = (int) (session.getAttribute("user_id"));
    model.addAttribute("user_id", user_id);
    model.addAttribute("product_type", "PC");
    LocalDate current_date = LocalDate.now();
    model.addAttribute("current_date", current_date);
    List<UserAddress> userAddressList = userAddressRepository.findUserAddressesByUserId(user_id);
    model.addAttribute("userAddressList", userAddressList);
    return "clean_schedule_pc";
  }

  @PostMapping("/confirm-booking")
  public String confirm(HttpServletRequest request, Model model,
      Authentication authentication, HttpSession session) {
    String product_type = request.getParameter("product_type");
    int user_id = (int) session.getAttribute("user_id");
    String status = request.getParameter("status");
    String customer_name = request.getParameter("customer_name");
    String customer_phone_number = request.getParameter("customer_phone_number");
    String customer_email = request.getParameter("customer_email");
    String time = request.getParameter("time");
    String notes = request.getParameter("notes");
    String dateString = request.getParameter("date");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;

    try {
      date = format.parse(dateString);
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    List<Schedule> scheduleList = scheduleRepository.findSchedulesByDate(date);

    int staff_id = 1;
    int address_id = userAddressRepository.findUserAddressByUserIdOrderByAddressIdAsc(user_id)
        .getAddressId();
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();
      if (principal instanceof OAuth2User) {
        OAuth2User oauth2User = (OAuth2User) principal;
        String email = (String) oauth2User.getAttribute("email");
        int id = userRepository.findUserByUserEmail(email).getUserId();
        session.setAttribute("user_id", id);
      } else if (principal instanceof UserDetails userDetails) {
        String email = userDetails.getUsername();
        user_id = userRepository.findUserByUserEmail(email).getUserId();
      }

    }
    if (product_type.equalsIgnoreCase("Laptop")) {

      int price = 100000;

      return getString(model, product_type, user_id, status, customer_name, customer_phone_number,
          customer_email, time, notes, date, scheduleList, staff_id, address_id, price);

    } else if (product_type.equalsIgnoreCase("Macbook")) {
      int price = 200000;
      return getString(model, product_type, user_id, status, customer_name, customer_phone_number,
          customer_email, time, notes, date, scheduleList, staff_id, address_id, price);

    } else {
      int selectedAddress = Integer.parseInt(request.getParameter("address"));
      List<UserAddress> userAddressList = userAddressRepository.findUserAddressesByUserId(user_id);
      model.addAttribute("userAddressList", userAddressList);
      int price = 300000;

      if (selectedAddress == 1) {
        int addressId = Integer.parseInt(request.getParameter("user_address"));
        customer_name = userAddressRepository.findUserAddressByAddressId(addressId).getReceive_name();
        customer_phone_number = userAddressRepository.findUserAddressByAddressId(addressId).getReceive_phone();
        if (scheduleService.getAvailableTime(time, scheduleList)) {
          Schedule schedule = new Schedule(user_id, time, status, notes, staff_id,
              addressId, product_type, customer_name, customer_phone_number,
              customer_email, price, date);
          scheduleRepository.save(schedule);
          userService.sendSchedulePcBookingEmail(customer_email,addressId,date
              ,time,notes);
          return "success";
        } else {
          model.addAttribute("address", selectedAddress);
          model.addAttribute("product_type", product_type);
          model.addAttribute("user_id", user_id);
          model.addAttribute("status", status);
          model.addAttribute("customer_name", customer_name);
          model.addAttribute("customer_phone_number", customer_phone_number);
          model.addAttribute("customer_email", customer_email);
          model.addAttribute("notes", notes);
          SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
          long timeInMillis = date.getTime();

          // Tạo một đối tượng Date mới từ thời gian đã giảm
          Date newDate = new Date(timeInMillis);

          // Định dạng lại Date mới thành chuỗi "yyyy-MM-dd"
          String formattedDate = outputFormat.format(newDate);

          model.addAttribute("current_date", formattedDate);
          System.out.println(date);
          model.addAttribute("staff_id", staff_id);

          model.addAttribute("error", "error");
          return "clean_schedule_pc";
        }

      } else {
        String district = request.getParameter("district");
        String commute = request.getParameter("commute");
        String detailAddress = request.getParameter("detail_address");
        String city = request.getParameter("city");
        String receiver_name = request.getParameter("receiver_name");
        String receiver_phone = request.getParameter("receiver_phone");
        int addressId = 0;
        if (scheduleService.getAvailableTime(time, scheduleList)) {
          UserAddress userAddress = new UserAddress(district, commute, detailAddress, city,
              receiver_name, receiver_phone, user_id);
          userAddressRepository.save(userAddress);
         addressId =  userAddress.getAddressId();
          Schedule schedule = new Schedule(user_id, time, status, notes, staff_id,
              addressId, product_type, receiver_name, receiver_phone,
              customer_email, price, date);
          scheduleRepository.save(schedule);
          userService.sendSchedulePcBookingEmail(customer_email,addressId,date
          ,time,notes);
          return "success";
        } else {
          model.addAttribute("product_type", product_type);
          model.addAttribute("user_id", user_id);
          model.addAttribute("status", status);
          model.addAttribute("address", selectedAddress);
          model.addAttribute("customer_email", customer_email);
          model.addAttribute("notes", notes);

          model.addAttribute("time",time);
          SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
          long timeInMillis = date.getTime();

          // Tạo một đối tượng Date mới từ thời gian đã giảm
          Date newDate = new Date(timeInMillis);

          // Định dạng lại Date mới thành chuỗi "yyyy-MM-dd"
          String formattedDate = outputFormat.format(newDate);

          model.addAttribute("current_date", formattedDate);
          System.out.println(date);
          model.addAttribute("staff_id", staff_id);
          model.addAttribute("address_id",  addressId);
          model.addAttribute("district", district);
          model.addAttribute("commute", commute);
          model.addAttribute("detailAddress", detailAddress);
          model.addAttribute("city", city);
          model.addAttribute("receiver_name", receiver_name);
          model.addAttribute("receiver_phone", receiver_phone);
          model.addAttribute("error", "error");
          return "clean_schedule_pc";
        }

      }
    }

  }

  // check available time for laptop & macbook clean booking
  // true => create new schedule obj
  // false => return with error noti
  private String getString(Model model, String product_type, int user_id, String status,
      String customer_name, String customer_phone_number, String customer_email, String time,
      String notes, Date date, List<Schedule> scheduleList, int staff_id, int address_id,
      int price) {

    if (scheduleService.getAvailableTime(time, scheduleList)) {
      Schedule schedule = new Schedule(user_id, time, status, notes, staff_id,
          address_id, product_type, customer_name, customer_phone_number,
          customer_email, price, date);
      scheduleRepository.save(schedule);
      userService.sendScheduleConfirmedEmail(customer_email,customer_name,customer_phone_number,
          date,time,notes);
      return "success";
    } else {
      model.addAttribute("product_type", product_type);
      model.addAttribute("user_id", user_id);
      model.addAttribute("status", status);
      model.addAttribute("customer_name", customer_name);
      model.addAttribute("customer_phone_number", customer_phone_number);
      model.addAttribute("customer_email", customer_email);
      model.addAttribute("notes", notes);
      SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
      long timeInMillis = date.getTime();

      // Tạo một đối tượng Date mới từ thời gian đã giảm
      Date newDate = new Date(timeInMillis);

      // Định dạng lại Date mới thành chuỗi "yyyy-MM-dd"
      String formattedDate = outputFormat.format(newDate);

      model.addAttribute("current_date", formattedDate);
      System.out.println(date);
      model.addAttribute("staff_id", staff_id);
      model.addAttribute("address_id", address_id);
      model.addAttribute("error", "error");
      return "clean_schedule";
    }
  }


}
