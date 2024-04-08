package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.repository.UserRepository;
import com.example.EcommerceStore.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.EcommerceStore.repository.ProductRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/EcommerceStore")
@Controller
public class ProductController {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductServiceImpl productService;

  @GetMapping("/product")
  public String getProduct(Authentication authentication, Model model,
      @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, HttpSession session) {
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails userDetails) {
        // Standard UserDetails case
        String email = userDetails.getUsername();
        model.addAttribute("user_email", email);
        session.setAttribute("user_email", email);
        User user = userRepository.findByUserEmail(email);
        model.addAttribute("userRepository", userRepository);
        session.setAttribute("user", user);
        System.out.println("User: " + user.getUserId());

        int user_id = user.getUserId();
        model.addAttribute("user_id", user_id);
      } else if (principal instanceof OAuth2User oAuth2User) {
        // get user_email when sign in with google or facebook
        Map<String, Object> attributes = oAuth2User.getAttributes();
        model.addAttribute("user_email",
            attributes.get("email"));
        session.setAttribute("user_email", attributes.get("email"));

        model.addAttribute("userRepository", userRepository);
        User user = userRepository.findByUserEmail((String) attributes.get("email"));
        session.setAttribute("user", user);
//        System.out.println("User: "+user.getUserId());

      } else {
        return "error";
      }
    }

    List<Product> productList = productService.getInitialProducts();
    model.addAttribute("productList", productList);

    List<Product> listPhone = productRepository.findProductByProductType("Phone");
    model.addAttribute("listPhone", listPhone);
    List<Product> listLaptop = productRepository.findProductByProductType("Laptop");
    model.addAttribute("listLaptop", listLaptop);
    List<Product> listPc = productRepository.findProductByProductType("PC");
    model.addAttribute("listPC",listPc);
    List<Product> listEarPhone = productRepository.findProductByProductType("Ear Phone");
    model.addAttribute("listEarPhone", listEarPhone);
    return "test_homepage";
  }

  @GetMapping("/products/more")
  public String getMoreProduct(Model model, @RequestParam int page, @RequestParam int size) {
    List<Product> moreProducts = productService.getMoreProducts(page, size);
    model.addAttribute("productListMore", moreProducts);

    return "homepage";
  }

  @GetMapping("/productDetails/{productId}")
  public String getProductDetails(@PathVariable("productId") Integer productId,
      Authentication authentication, Model model, HttpSession session) {
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof UserDetails userDetails) {
        // Standard UserDetails case
        String email = userDetails.getUsername();
        model.addAttribute("user_email", email);
        User user = userRepository.findByUserEmail(email);

        model.addAttribute("userRepository", userRepository);
        int user_id = user.getUserId();
        model.addAttribute("user_id", user_id);
      } else if (principal instanceof OAuth2User oAuth2User) {
        // get user_email when sign in with google or facebook
        Map<String, Object> attributes = oAuth2User.getAttributes();
        model.addAttribute("user_email",
            attributes.get("email"));
        model.addAttribute("userRepository", userRepository);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

      } else {
        return "error";
      }

    } else {
      model.addAttribute("user_email", " ");
      model.addAttribute("user_id", -1);
      model.addAttribute("userRepository", userRepository);

    }
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isPresent()) {
      model.addAttribute("productRepository", productRepository);
      model.addAttribute("product", optionalProduct.get());
      return "productDetails";
    }

    return "error";
  }

  @GetMapping("/productFilter/{product_type}")
  public String productFilter(@PathVariable("product_type") String product_type, Model model
      , HttpSession session) {
    List<Product> listProduct = productService.searchFilteredProduct(product_type);
    model.addAttribute("listProduct", listProduct);
    String email = String.valueOf(session.getAttribute("user_email"));
    model.addAttribute("user_email", email);
    model.addAttribute("productType", product_type);
    User user = (User) session.getAttribute("user");
    model.addAttribute("user", user);
//    System.out.println("User id in product filter: "+ user.getUserId());
    return "productFilter_test";
  }

  @GetMapping("/productFilter/more")
  public String getMoreFilteredProduct(
      HttpServletRequest request, Model model, @RequestParam int page, @RequestParam int size
  ) {
    String product_type = request.getParameter("product_type");
    List<Product> moreProducts = productService.getMoreSearchFilteredProduct(product_type, page,
        size);
    model.addAttribute("productListMore", moreProducts);
    System.out.println(product_type);
    if (moreProducts.isEmpty()) {
      System.out.println("empty");
    }
    for (Product p : moreProducts) {
      System.out.println(p);
    }
    return "productFilter_test";
  }

  @GetMapping("/productBrandFilter/{product_brand}")
  public String findByProductBrand(@PathVariable("product_brand") String product_brand,
      Model model, HttpSession session) {
    List<Product> listProduct = productRepository.findProductsByProductBrand(product_brand);
    model.addAttribute("listProduct", listProduct);
    String email = String.valueOf(session.getAttribute("user_email"));
    model.addAttribute("user_email", email);
    model.addAttribute("productType", "Laptop");
    User user = (User) session.getAttribute("user");
    model.addAttribute("user", user);
    return "productFilter_test";
  }

  @GetMapping("/productFilter")
  public String findProductByPrice(@RequestParam("start_price") int start_price,
      @RequestParam("end_price") int end_price,
      @RequestParam("product_type") String productType,
      Model model, HttpSession session) {
    List<Product> listProduct = productService.getProductFilterByPrice(
        productType,
        start_price, end_price);


    String email = String.valueOf(session.getAttribute("user_email"));
    model.addAttribute("user_email", email);
    if (!listProduct.isEmpty()) {
      model.addAttribute("listProduct", listProduct);
    }

    model.addAttribute("productType", productType);
    model.addAttribute("start_price", start_price);
    model.addAttribute("end_price", end_price);
    User user = (User) session.getAttribute("user");
    model.addAttribute("user", user);
    return "productFilter_test";
  }

  @GetMapping("/productFilter/price/more")
  public String findMoreProductByPrice(@RequestParam("start_price") int start_price,
      @RequestParam("end_price") int end_price, @RequestParam("product_type") String productType,
      Model model, @RequestParam int page, @RequestParam int size,
      HttpSession session) {
    List<Product> productListMore = productService.getMoreProductFilterByPrice(productType,
        start_price, end_price, page, size);
    model.addAttribute("productListMore", productListMore);
    model.addAttribute("product_type", productType);
    model.addAttribute("start_price", start_price);
    model.addAttribute("end_price", end_price);
    User user = (User) session.getAttribute("user");
    model.addAttribute("user", user);
    return "productFilter_test";
  }

  @GetMapping("/search")
  public String searchProduct(@RequestParam String keyword,
      @RequestParam int page, @RequestParam int size,
      Model model, HttpSession session) {

    List<Product> productList = productService.searchProduct(keyword);
    List<Product> pList = productService.getMoreSearchProduct(keyword, page, size);
    model.addAttribute("productList", productList);
    model.addAttribute("productListMore", pList);
    model.addAttribute("keyword", keyword);
    User user = (User) session.getAttribute("user");
    model.addAttribute("user", user);
    String email = String.valueOf(session.getAttribute("user_email"));
    model.addAttribute("user_email", email);
    return "test_homepage";
  }

  @GetMapping("/searchFilter")
  public String searchFilteredProduct(@RequestParam String keyword,
      @RequestParam int page, @RequestParam int size,
      Model model, HttpSession session) {

    List<Product> productList = productService.searchProduct(keyword);
    List<Product> pList = productService.getMoreSearchProduct(keyword, page, size);
    model.addAttribute("productList", productList);
    model.addAttribute("productListMore", pList);
    model.addAttribute("keyword", keyword);
    User user = (User) session.getAttribute("user");
    model.addAttribute("user", user);
    String email = String.valueOf(session.getAttribute("user_email"));
    model.addAttribute("user_email", email);
    return "productFilter_test";
  }

}
