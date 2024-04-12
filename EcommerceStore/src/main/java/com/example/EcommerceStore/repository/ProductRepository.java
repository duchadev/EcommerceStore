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
  @Query("SELECT p FROM Product p WHERE p.productType = :product_type")
  Page<Product> searchFilterProducts(String product_type, PageRequest pageRequest);

  @Query(value = "select * from Product p where p.pc_id = ?1 ", nativeQuery = true)
  List<Product> findProductByPc_id(int pc_id);
  @Query(value="SELECT * FROM Product p WHERE p.product_type = :productType AND p.product_price >= :startPrice AND p.product_price <= :endPrice", nativeQuery = true)
  Page<Product> getFilteredProductByPrice(String productType, int startPrice, int endPrice, PageRequest pageRequest);
  @Query("SELECT p FROM Product p WHERE p.productType = :product_type and p.pc_id = :pc_id")
  List<Product> findProductsByProductTypeAndPc_id(String product_type, int pc_id);
  @Query("SELECT DISTINCT p FROM Product p JOIN p.orderDetailList od JOIN od.order o JOIN o.address.user u WHERE u.userId = :userId AND p NOT IN (SELECT DISTINCT f.product FROM Feedback f WHERE f.user.userId = :userId)")
  List<Product> findProductsToFeedbackByUser(@Param("userId") int userId);
}
