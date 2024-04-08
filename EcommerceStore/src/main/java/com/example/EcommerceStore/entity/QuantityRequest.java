package com.example.EcommerceStore.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantityRequest {
private int productId;
private int quantity;
}
