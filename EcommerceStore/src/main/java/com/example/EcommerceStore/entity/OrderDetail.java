package com.example.EcommerceStore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name= "order_detail")
public class OrderDetail {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "order_detail_id")
 private int order_detail_id;

 @Column(name = "product_id", insertable = false, updatable = false) // Specify insertable and updatable attributes
 private int product_id;
 private int quantity;
 private String product_name;
 private String image;

 @ManyToOne
 @JoinColumn(name = "order_id")
 private Order order;

 @JsonBackReference(value = "orderDetail_product")
 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "product_id", referencedColumnName = "product_id") // Specify referencedColumnName to point to the correct column in Product entity
 private Product product;
}
