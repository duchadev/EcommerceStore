package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
  Page<Product> getAll(Integer pageNo);
  Page<Product> searchProduct(String keyword, Integer pageNo);

}
