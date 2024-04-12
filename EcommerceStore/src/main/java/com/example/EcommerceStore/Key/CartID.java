package com.example.EcommerceStore.Key;

import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CartID implements Serializable {
  @JsonBackReference(value = "cart_user")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="user_id")
  private User user;
  @JsonBackReference(value = "cart_product")
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="product_id")
  private Product product;
}
