package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "ProductStaff")
public class ProductStaff {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="staff_id")
  private int product_staff_id;
  private String staff_name;
  private String staff_phone_number;
  private String staff_address;
  private Date staff_birthday;
}
