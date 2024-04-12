package com.example.EcommerceStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Feedback")
public class Feedback {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="feedback_id")
  private long feedbackID;
  private int rating;
  @Column(name="feedback_content")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
}
