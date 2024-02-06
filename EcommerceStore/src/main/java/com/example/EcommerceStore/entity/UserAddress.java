package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserAddress {

  @Id
  @Column(name = "address_id")
  private int addressId;
  @Column(name = "user_id")
  private int userId;
  private String district;
  private String commute;
  @Column(name = "detail_address")
  private String detailAddress;
  private String city;
  private String receive_name;
  private String receive_phone;

  @Override
  public String toString() {
    return district + ", " + commute + ", " + detailAddress + ", " + city ;
  }
}
