package com.example.EcommerceStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

public class PC {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pc_id")
  private int pc_id;
  private String pc_name;
  private int pc_price;
  private String pc_description;
  private int pc_rating;
  private String pc_image;


}
