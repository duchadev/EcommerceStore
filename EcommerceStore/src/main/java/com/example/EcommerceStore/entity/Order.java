package com.example.EcommerceStore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="dbo_order")
@ToString
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private long orderId;
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
  @JsonManagedReference(value = "orderDetail_order")
  @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
  private List<OrderDetail> orderDetailList;

  @JsonBackReference(value = "order_address")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="address_id", referencedColumnName = "address_id", insertable=false, updatable=false)
  private Address address;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Order order)) return false;
    return getOrderId() == order.getOrderId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getOrderId());
  }

}
