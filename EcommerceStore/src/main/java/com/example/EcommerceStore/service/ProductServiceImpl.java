package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Page<Product> getAll(Integer pageNo) {
    Pageable pageable = PageRequest.of(pageNo - 1, 4);
    return this.productRepository.findAll(pageable);
  }

  @Override
  public Page<Product> searchProduct(String keyword, Integer pageNo) {
    List<Product> list = productRepository.searchProduct(keyword);
    Pageable pageable = PageRequest.of(pageNo - 1, 4);
    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), list.size());
    list = list.subList(start, end);

    // get the total count of products matching the keyword
    long totalCount = productRepository.searchProduct(keyword).size();

    return new PageImpl<>(list, pageable, totalCount);
  }

}
