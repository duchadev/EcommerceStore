package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="pc_building_item")
public class PcBuildingItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pc_building_item_id")
  private int pc_building_item_id;
  @Column(name="product_id")
  private int product_id;
  private int quantity;
  @Column(name="pc_building_id")
  private int  pc_building_id;
  @ManyToOne
  @JoinColumn(name = "pc_building_id", insertable = false, updatable = false)
  private PcBuilding pcBuilding;

}
