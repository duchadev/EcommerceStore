package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Cart;
import com.example.EcommerceStore.entity.CartItem;
import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.repository.CartItemRepository;
import com.example.EcommerceStore.repository.CartRepository;
import com.example.EcommerceStore.repository.ProductRepository;
import com.example.EcommerceStore.repository.UserRepository;
import com.microsoft.sqlserver.jdbc.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/EcommerceStore")
public class CartController {

  @Autowired
  CartRepository cartRepository;
  @Autowired
  CartItemRepository cartItemRepository;
  @Autowired
  ProductRepository productRepository;
  @Autowired
  UserRepository userRepository;


  @GetMapping("/cart/{user_id}")
  public String viewCart(@PathVariable("user_id") int user_id, Model model, HttpSession session) {
    Cart cart = cartRepository.findCartByUserId(user_id);

    if (cart == null) {
      int total = 0;
      model.addAttribute("total", total);
      model.addAttribute("error", "Your cart is empty");
    } else {
      List<CartItem> cartItemList = cartItemRepository.findCartItemsByCartId(cart.getCartId());
      model.addAttribute("productRepository", productRepository);
      model.addAttribute("cartItemList", cartItemList);
      model.addAttribute("user_id", user_id);
      session.setAttribute("cartItemList", cartItemList);
      int total = getTotal(cartItemList);
      int item_quantity =0;
      if (cartItemList == null) {
        total = 0;
        model.addAttribute("total", total);
        model.addAttribute("item_quantity", item_quantity);
      } else {
        model.addAttribute("total", total);
        for(CartItem c: cartItemList)
        {
          item_quantity+=c.getQuantity();
        }
        model.addAttribute("item_quantity", item_quantity);
      }


    }

    return "cart_test";
  }

  @PostMapping("/cart/add")
  public  ResponseEntity<String> addToCart(HttpServletRequest request, Model model,
      HttpSession session, HttpServletResponse response) {
    try {
      int product_id = Integer.parseInt(request.getParameter("product_id"));
      int user_id = Integer.parseInt(request.getParameter("user_id"));
      String quantityString = request.getParameter("quantity");
      System.out.println(product_id);
      System.out.println(user_id);

      // Validate quantity parameter
      if (!StringUtils.isNumeric(quantityString)) {
        return ResponseEntity.badRequest().body("Error");
      }

      int quantity = Integer.parseInt(quantityString);
      System.out.println(quantity);
      Optional<Product> productOptional = productRepository.findById(product_id);

      if (productOptional.isPresent()) {
        Product product = productOptional.get();

        // retrieve or create a user based on the user_id
        User user = userRepository.findUserByUserId(user_id);

        if (user != null) {
          Cart cart = getCurrentUserCart(user.getUserId());
          Optional<CartItem> existingItem = cart.getCartItemList().stream()
              .filter(item -> item.getProductId() == (product.getProductId()))
              .findFirst();
          cart.setUserId(user_id);
          cartRepository.save(cart);

          if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
            session.setAttribute("cartItemList", existingItem.stream().toList());
            model.addAttribute("cartItemList", existingItem.stream().toList());
            cartItemRepository.save(existingItem.get());
          } else {
            // create new cart_item if product have not been in cart yet
            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getProductId());
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setCartId(cart.getCartId());
            cart.getCartItemList().add(cartItem);

            cartItemRepository.save(cartItem);

          }

          List<CartItem> cartItemList = cartItemRepository.findCartItemsByCartId(cart.getCartId());
          model.addAttribute("cartItemList", cartItemList);
          session.setAttribute("cartItemList", cartItemList);
          int total = getTotal(cartItemList);
          if (cartItemList == null) {
            total = 0;
            model.addAttribute("total", total);
          } else {
            model.addAttribute("total", total);
          }
          model.addAttribute("productRepository", productRepository);
          model.addAttribute("user_id", user_id);
          // Set the user in the cart
          model.addAttribute("userRepository", userRepository);
          System.out.println("ran");
          session.setAttribute("user", user);

          // Redirect to the cart page
          response.sendRedirect("/EcommerceStore/cart/"+user_id);

          return ResponseEntity.ok("Added to cart");
        } else {
          return ResponseEntity.badRequest().body("User not found");
        }
      } else {
        // handle product not found
        return ResponseEntity.badRequest().body("Error");
      }
    } catch (NumberFormatException ex) {
      // handle invalid integer format for quantity
      return ResponseEntity.badRequest().body("Error");
    } catch (Exception ex) {
      model.addAttribute("error", ex);
      return ResponseEntity.badRequest().body("Error");
        }
  }


  // get cart of current user
  private Cart getCurrentUserCart(int user_id) {
    Cart c = cartRepository.findCartByUserId(user_id);
    if (c != null) {
      return cartRepository.findCartByUserId(user_id);
    } else {
      return new Cart();
    }

  }

  // update quantity of product
  @PostMapping("/cart/updateQuantity")
  public String updateQuantity(@RequestParam("cartItemId") int cartItemId,

      @RequestParam("action") String action,
      @RequestParam("user_id") int user_id,
      Model model, HttpSession session) {
    try {
      CartItem cartItem = cartItemRepository.findById(cartItemId)
          .orElseThrow(() -> new IllegalArgumentException("Invalid cart item Id: " + cartItemId));
      Cart cart = cartRepository.findCartByUserId(user_id);
      if ("increase".equalsIgnoreCase(action)) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);

        setModel(model, cart);

//
//        System.out.println("Product ID: " + cartItem.getProductId());
//        System.out.println("Quantity: "+ cartItem.getQuantity());
      } else if ("decrease".equalsIgnoreCase(action)) {
        if (cartItem.getQuantity() > 1) {
          cartItem.setQuantity(cartItem.getQuantity() - 1);
          cartItemRepository.save(cartItem);
          setModel(model, cart);
//          System.out.println("Product ID: " + cartItem.getProductId());
//          System.out.println("Quantity: "+ cartItem.getQuantity());
        } else {
          setModel(model, cart);
          removeItem(cartItemId, user_id, model);
          return "cart_test";
        }
      }

      List<CartItem> cartItemList = cartItemRepository.findCartItemsByCartId(cart.getCartId());
      session.setAttribute("cartItemList", cartItemList);
      model.addAttribute("cartItemList", cartItemList);
      int total = getTotal(cartItemList);
      if (cartItemList == null) {
        total = 0;
        model.addAttribute("total", total);
      } else {
        model.addAttribute("total", total);
      }
      model.addAttribute("productRepository", productRepository);
      model.addAttribute("user_id", user_id);
      cartItemRepository.save(cartItem);

      return "cart_test";
    } catch (Exception ex) {
      model.addAttribute("error", ex.getMessage());
      return "error";
    }
  }
// set quantity and total to view
  private void setModel(Model model, Cart cart) {
    List<CartItem> cartItemList = cartItemRepository.findCartItemsByCartId(cart.getCartId());

    int total = getTotal(cartItemList);
    int item_quantity =0;
    if (cartItemList == null) {
      total = 0;
      model.addAttribute("total", total);
      model.addAttribute("item_quantity", item_quantity);
    } else {
      model.addAttribute("total", total);
      for(CartItem c: cartItemList)
      {
        item_quantity+=c.getQuantity();
      }
      model.addAttribute("item_quantity", item_quantity);
    }
  }

  @PostMapping("/cart/removeItem")
  public String removeItem(@RequestParam("cartItemId") int cartItemId,
      @RequestParam("user_id") int user_id, Model model) {
    try {
      CartItem cartItem = cartItemRepository.findById(cartItemId)
          .orElseThrow(() -> new IllegalArgumentException("Invalid cart item Id: " + cartItemId));
      Cart cart = cartRepository.findCartByUserId(user_id);
      cartItemRepository.delete(cartItem);
      // You may want to update the cart or related data here if needed
      List<CartItem> cartItemList = cartItemRepository.findCartItemsByCartId(cart.getCartId());
      model.addAttribute("cartItemList", cartItemList);

      int total = getTotal(cartItemList);
      if (cartItemList == null) {
        total = 0;
        model.addAttribute("total", total);
      } else {
        model.addAttribute("total", total);
      }

      model.addAttribute("productRepository", productRepository);
      model.addAttribute("user_id", user_id);
      return "cart_test";
    } catch (Exception ex) {
      model.addAttribute("error", ex.getMessage());
      return "error";
    }

  }

  public int getTotal(List<CartItem> cartItemList) {
    if (cartItemList == null) {
      return 0;
    } else {
      int total = 0;
      for (CartItem c : cartItemList) {
        total += c.getQuantity() * productRepository.getProductByProductId(c.getProductId())
            .getProductPrice();
      }
      return total;
    }


  }

}