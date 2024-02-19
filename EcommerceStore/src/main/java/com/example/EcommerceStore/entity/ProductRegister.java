package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegister {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="pRegister_id")
  private int pRegister_id;
@Column(name="product_id")
  private int productId;
  private String user_email;
  public ProductRegister(int product_id, String user_email)
  {
    this.productId = product_id;
    this.user_email= user_email;
  }
}
