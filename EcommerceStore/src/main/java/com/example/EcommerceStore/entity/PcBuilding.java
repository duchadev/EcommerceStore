package com.example.EcommerceStore.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
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
@Table(name="pc_building")
public class PcBuilding {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int pc_building_id;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "pc_id")
  private int pcId;
  @OneToMany(mappedBy = "pc_building_item_id", cascade = CascadeType.ALL)
  private List<PcBuildingItem> pcBuildingItems = new ArrayList<>();

}
