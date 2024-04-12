package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "dbo_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="user_id")
  private int userId;

  @Column(name="user_name",unique = true)

  private String user_name;
  @Column(name="birthday")
  private Date birthday;
  @Column(name = "user_email")
  private String userEmail;

  @Column(name = "user_phone_number")
  private String user_phoneNumber;
  @Column(name = "password")
  private String password;
  private String roles;
  private String otp;
  private LocalDateTime otpGeneratedTime;
  @Column(name = "is_active")
  private int verified;
}