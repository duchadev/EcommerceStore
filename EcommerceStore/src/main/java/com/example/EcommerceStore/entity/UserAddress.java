package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="address")
public class UserAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
 public String getCusInfo()
 {
   return receive_name+", "+ receive_phone;
 }
  public UserAddress(String district, String commute, String detailAddress, String city,
      String receive_name ,String receive_phone, int userId)
  {
    this.userId = userId;
    this.district = district;
    this.commute = commute;
    this.detailAddress = detailAddress;
    this.city = city;
    this.receive_name = receive_name;
    this.receive_phone= receive_phone;
  }
}
