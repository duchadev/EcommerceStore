package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.OrderDetail;
import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.Staff;
import java.sql.Date;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductService {
  Page<Product> getAll(Integer pageNo);
  Page<Product> searchProduct(String keyword, Integer pageNo);
  List<Product> getInitialProducts();
  List<Product>searchProduct(String keyword);
  List<Product>getMoreSearchProduct(String keyword, int page, int size);
  List<Product>searchFilteredProduct(String product_type);
  List<Product> getMoreSearchFilteredProduct(String keyword, int page, int size);
  List<Product> getMoreProducts(int page, int size);
  List<Product> getProductFilterByPrice(String product_type, int start, int end);
  List<Product> getMoreProductFilterByPrice(String product_type, int start, int end
  , int page, int size);
  void save(Product product);
  Product get(int product_id);
  void delete(int id);
  List<Product> listAll();
   void setRating(Product product);
  public List<OrderDetail> getAllUserBoughtByUserId(int userId) ;
}
