package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private int productId;
  private String product_name;
  private int product_quantity;
  @Column(name="product_price")
  private int productPrice;
  private String product_image;
  private int rating;
  @Column(name="product_brand")
  private String productBrand;
  private String productType;

  @Column(name="pc_id")
  private int pc_id;
  public Product(int productId,String productName,int product_quantity,String product_image,
      int rating,String product_brand, int product_price,String product_type
  )

  {
    this.productId = productId;
    this.product_name = productName;
    this.product_quantity = product_quantity;
    this.product_image = product_image;
    this.rating = rating;
    this.productBrand = product_brand;
    this.productType = product_type;
    this.productPrice = product_price;

  }
}
