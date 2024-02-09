package com.example.EcommerceStore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="[Order]")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private int order_id;
  @Column(name="user_id")
  private int userId;
  private int address_id;
  private double cost;
  private String status;
  private Date order_date;
  //payment status
  private String pStatus;
  public Order( int address_id, int cost, String status,String pStatus,int userId, Date order_date) {
    this.userId = userId;
    this.address_id = address_id;
    this.cost = cost;
    this.status = status;
    this.pStatus = pStatus;
    this.order_date = order_date;

  }
  @OneToMany(mappedBy = "order", cascade  = CascadeType.ALL)
  private List<OrderDetail> orderDetails = new ArrayList<>();
}
