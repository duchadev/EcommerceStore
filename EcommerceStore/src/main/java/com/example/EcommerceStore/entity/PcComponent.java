package com.example.EcommerceStore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
public class PcComponent {
@Id
  private int component_id;

  @ManyToOne
  @JoinColumn(name = "pc_id", nullable = false)
  private PC pc;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

}
