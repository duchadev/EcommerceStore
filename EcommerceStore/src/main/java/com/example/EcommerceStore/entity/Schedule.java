package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "schedule_id")
  private int schedule_id;
  private int user_id;
  private String time;
  private String status;
  private String notes;

  private int staff_id;

  private int address_id;
  private String product_type;
  private String customer_name;
  private String customer_phone_number;
  private String customer_email;
  private int price;
  private Date date;

  public Schedule(int user_id, String time, String status, String notes, int staff_id,
      int address_id, String product_type, String customer_name,
      String customer_phone_number, String customer_email, int price, Date date) {
    this.user_id = user_id;
    this.time = time;
    this.status = status;
    this.notes = notes;
    this.staff_id = staff_id;
    this.address_id = address_id;
    this.product_type = product_type;
    this.customer_name = customer_name;
    this.customer_phone_number = customer_phone_number;
    this.customer_email = customer_email;
    this.price = price;
    this.date = date;
  }
}
