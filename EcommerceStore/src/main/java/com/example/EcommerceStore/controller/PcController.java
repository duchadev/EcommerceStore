package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PcController {
  public List<Product> listComponent = new ArrayList<>();
  @Autowired
  ProductRepository productRepository;
  @GetMapping("/pc-building/get-component/{product_type}")
  public @ResponseBody List<Product> getComponent(@PathVariable("product_type") String productType) {
    List<Product> productList = productRepository.findProductByProductType(productType);
    return productList;
  }
//  @GetMapping("/pc-building/add-product-to-list")
//  public Product addToList(@RequestParam("product_type") String product_type,
//      @RequestParam("pc_id") int pc_id, Model model,@RequestParam("product_id") int product_id)
//  {
//    Product cpu = null;
//    Product main = null;
//    Product ram = null;
//    Product ssd = null;
//    Product hdd = null;
//    Product vga = null;
//    Product powerSupply = null;
//    Product cases = null;
//    Product fan = null;
//    Product waterCooling = null;
//    Product screen = null;
//    Product keyboard = null;
//    Product mouse = null;
//    Product earphone = null;
//    Product p = productRepository.findProductByProductId(product_id);
//    if (p != null) {
//      String productType = p.getProductType();
//      switch (productType.toUpperCase().trim()) {
//        case "CPU":
//          cpu = p;
//          break;
//        case "MAIN":
//          main = p;
//          break;
//        case "RAM":
//          ram = p;
//          break;
//        case "SSD":
//          ssd = p;
//          break;
//        case "HDD":
//          hdd = p;
//          break;
//        case "VGA":
//          vga = p;
//          break;
//        case "POWERSUPPLY":
//          powerSupply = p;
//          break;
//        case "CASES":
//          cases = p;
//          break;
//        case "COOLER":
//          fan = p;
//          break;
//        case "WATERCOOLING":
//          waterCooling = p;
//          break;
//        case "SCREEN":
//          screen = p;
//          break;
//        case "KEYBOARD":
//          keyboard = p;
//          break;
//        case "MOUSE":
//          mouse = p;
//          break;
//        case "EARPHONE":
//          earphone = p;
//          break;
//        default:
//          System.out.println("Invalid product type: " + productType);
//          break;
//      }
//    } else {
//      System.out.println("Product not found for product id: " + product_id);
//    }
//
//    listComponent.add(p);
//    model.addAttribute("listComponent", listComponent);
//    for(Product product: listComponent)
//    {
//      System.out.println(product.toString());
//    }
//    model.addAttribute("cpu",cpu);
//    model.addAttribute("main",main);
//    model.addAttribute("ram",ram);
//    model.addAttribute("ssd",ssd);
//    model.addAttribute("hdd",hdd);
//    model.addAttribute("vga",vga);
//    model.addAttribute("powerSupply",powerSupply);
//    model.addAttribute("cases",cases);
//    model.addAttribute("fan",fan);
//    model.addAttribute("waterCooling",waterCooling);
//    model.addAttribute("screen",screen);
//    model.addAttribute("earphone",earphone);
//    model.addAttribute("keyboard",keyboard);
//    model.addAttribute("mouse",mouse);
//    System.out.println("ADDED: "+p);
//
//    return p;
//
//  }
}
