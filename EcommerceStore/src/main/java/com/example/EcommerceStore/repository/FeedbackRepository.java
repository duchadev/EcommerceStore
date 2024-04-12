package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
  @Query("SELECT f FROM Feedback f WHERE f.product.productId = :productId")
  List<Feedback> findAllByProductId(int productId);

  @Query("SELECT COUNT(f) FROM Feedback f WHERE f.product.productId = :productId")
  int countByProductId(int productId);

  @Query("SELECT SUM(f.rating) FROM Feedback f WHERE f.product.productId = :productId")
  Integer sumRatingByProductId(int productId);
}
