package com.example.EcommerceStore.service.impl;

import com.example.EcommerceStore.entity.Order;
import com.example.EcommerceStore.entity.OrderDetail;
import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.repository.FeedbackRepository;
import com.example.EcommerceStore.repository.OrderRepository;
import com.example.EcommerceStore.repository.ProductRepository;
import com.example.EcommerceStore.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private FeedbackRepository feedbackRepository;
  @Autowired
  private OrderRepository orderRepository;

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

  public List<Product> getInitialProducts() {
    return productRepository.findAll(PageRequest.of(0, 4)).getContent();
  }
// get more product search function in home page
  public List<Product> searchProduct(String keyword) {
    // Perform the search using pagination
    Page<Product> searchPage = productRepository.searchProducts(keyword,
        PageRequest.of(0, 4));

    // Retrieve the content of the first page

    return searchPage.getContent();
  }
  public List<Product> getMoreSearchProduct(String keyword, int page, int size) {
    // Perform the search using pagination
    Page<Product> searchPage = productRepository.searchProducts(keyword,
        PageRequest.of(page, size));

    // Retrieve the content of the first page

    return searchPage.getContent();
  }
  // get more product in product filter
  public List<Product> searchFilteredProduct(String product_type)
  {
    Page<Product> searchPage = productRepository.searchFilterProducts(product_type,
        PageRequest.of(0,4));
    return searchPage.getContent();
  }
  public List<Product> getMoreSearchFilteredProduct(String keyword, int page, int size) {
    // Perform the search using pagination
    Page<Product> searchPage = productRepository.searchFilterProducts(keyword,
        PageRequest.of(page, size));

    // Retrieve the content of the first page

    return searchPage.getContent();
  }


  public List<Product> getMoreProducts(int page, int size) {
    return productRepository.findAll(PageRequest.of(page, size)).getContent();
  }
//get more product filtered by price
  @Override
  public List<Product> getProductFilterByPrice(String product_type, int start, int end) {
   Page<Product> filteredPage = productRepository.getFilteredProductByPrice(product_type,
        start,end, PageRequest.of(0,4));
    return filteredPage.getContent();
  }

  @Override
  public List<Product> getMoreProductFilterByPrice(String product_type, int start, int end,
      int page, int size) {
    Page<Product> filteredPage = productRepository.getFilteredProductByPrice(product_type,
        start,end, PageRequest.of(page,size));

    return filteredPage.getContent();
  }
  public void save(Product product) {
    productRepository.save(product);
  }

  public Product get(int id) {
    try{
      Optional<Product> result = productRepository.findById(id);
      if (result.isPresent()) {
        return result.get();
      }

    } catch (Exception ex)
    {
      ex.printStackTrace();
      System.out.println(ex);
    }
return null;

  }
  @Transactional
  public void delete(int id)  {
    try{
      Optional<Product> result = productRepository.findById(id);
      if (result.isPresent()) {
        productRepository.deleteById(id);
      }
    }catch (Exception ex)
    {
      System.out.println(ex);
      ex.printStackTrace();
    }


  }
  public List<Product> listAll() {
    return (List<Product>) productRepository.findAll(Sort.by(Sort.Direction.DESC, "product_id"));
  }
  public void setRating(Product product){
    product.setRating(feedbackRepository.sumRatingByProductId(product.getProductId()) / feedbackRepository.countByProductId(product.getProductId()));
    productRepository.save(product);
  }
  public List<OrderDetail> getAllUserBoughtByUserId(int userId) {
    try {
      List<Order> orderList = orderRepository.findOrdersByUserId(userId);
      List<OrderDetail> orderDetailList = new ArrayList<>();
      for (Order order : orderList) {
        System.err.println(order.getOrderId());
        for (OrderDetail od : order.getOrderDetails()) {
          System.err.println(od.getProduct().getProduct_name() + " od");
          orderDetailList.add(od);
        }
      }
      return orderDetailList;
    } catch (Exception e) {
      System.err.println("XXXXXXXXXXXXXXx");
      e.printStackTrace();
      return null;
    }
  }
}
