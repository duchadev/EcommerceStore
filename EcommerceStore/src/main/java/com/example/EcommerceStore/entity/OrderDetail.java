package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "order_detail_id")
 private int order_detail_id;

 private int product_id;
 private int quantity;
 private String product_name;
 private String image;

 @ManyToOne
 @JoinColumn(name = "order_id")
 private Order order;
}
