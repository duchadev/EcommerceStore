package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
  List<Product> findProductByProductType(String productType);
  Product findProductByProductId(int id);
  Product getProductByProductId(int product_id);
  List<Product> findProductsByProductBrand(String productBrand);
  List<Product> findProductsByProductPriceBetweenAndProductType(int start_price, int end_price, String productType);
   @Query("select p from Product p where p.product_name LIKE CONCAT('%', ?1, '%')")
  List<Product> searchProduct(String keyword);

  @Query("select p from Product p where p.product_name LIKE CONCAT('%', ?1, '%')")
  Page<Product> searchProducts(String keyword, PageRequest of);

  @Query(value = "select * from Product p where p.pc_id = ?1 ", nativeQuery = true)
  List<Product> findProductByPc_id(int pc_id);
}
