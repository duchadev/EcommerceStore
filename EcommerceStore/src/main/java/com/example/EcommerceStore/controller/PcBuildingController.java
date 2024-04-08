package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.Cart;
import com.example.EcommerceStore.entity.PC;
import com.example.EcommerceStore.entity.PcBuilding;
import com.example.EcommerceStore.entity.PcBuildingItem;
import com.example.EcommerceStore.entity.PcComponent;
import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.entity.QuantityRequest;
import com.example.EcommerceStore.repository.CartRepository;
import com.example.EcommerceStore.repository.PcBuildingItemRepository;
import com.example.EcommerceStore.repository.PcBuildingRepository;
import com.example.EcommerceStore.repository.PcRepository;
import com.example.EcommerceStore.repository.ProductRepository;
import com.example.EcommerceStore.repository.UserRepository;
import com.example.EcommerceStore.service.impl.PcBuildingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/EcommerceStore")
public class PcBuildingController {

  @Autowired
  PcRepository pcRepository;
  @Autowired
  PcBuildingRepository pcBuildingRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  ProductRepository productRepository;
  @Autowired
  PcBuildingItemRepository pcBuildingItemRepository;

  @Autowired
  PcBuildingServiceImpl pcBuildingService;
  @Autowired
  CartRepository cartRepository;

  List<Product> productList = new ArrayList<>();
  List<PcComponent> componentList = new ArrayList<>();

  @GetMapping("/build-pc")
  public String getMapping(Model model, HttpSession session) {
    List<PC> pcList = pcRepository.findAll();
    model.addAttribute("pcList", pcList);
    int user_id = (int) session.getAttribute("user_id");
    model.addAttribute("user_id", user_id);

    return "build_pc";
  }

  @GetMapping("/pc-building/build-pc-view")
  public String view(HttpServletRequest request, Model model, HttpSession session) {
    int pc_id = Integer.parseInt(request.getParameter("pc_id"));
    productList = productRepository.findProductByPc_id(pc_id);

    System.out.println("--------------------------------------");
    if(componentList.isEmpty())
    {
      for (Product product : productList) {
        PcComponent pcComponent = new PcComponent();
        pcComponent.setProduct_id(product.getProductId());

        pcComponent.setQuantity(1);
        pcComponent.setProduct_type(product.getProductType());
        componentList.add(pcComponent);
        System.out.println(pcComponent);
      }

    }

    Product cpu = null;
    Product main = null;
    Product ram = null;
    Product ssd = null;
    Product hdd = null;
    Product vga = null;
    Product powerSupply = null;
    Product cases = null;
    Product fan = null;
    Product waterCooling = null;
    Product screen = null;
    Product keyboard = null;
    Product mouse = null;
    Product earphone = null;
    for (PcComponent p : componentList) {
      System.out.println("Product number: " + p.getProduct_id());
//
      String productType = productRepository.findProductByProductId(p.getProduct_id())
          .getProductType();

      switch (productType.toUpperCase().trim()) {
        case "CPU":

          if (cpu == null) {
            cpu = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          cpu.setProductId(p.getProduct_id());

          cpu.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          cpu.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());
          cpu.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          cpu.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());
          break;
        case "MAIN":
          if (main == null) {
            main = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          main.setProductId(p.getProduct_id());

          main.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          main.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          main.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          main.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "RAM":
          if (ram == null) {
            ram = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          ram.setProductId(p.getProduct_id());
          System.out.println("product id cua ram: " + ram.getProductId());
          ram.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          ram.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          ram.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          ram.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "SSD":
          if (ssd == null) {
            ssd = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          ssd.setProductId(p.getProduct_id());
          ssd.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          ssd.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          ssd.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          ssd.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "HDD":
          if (hdd == null) {
            hdd = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          hdd.setProductId(p.getProduct_id());
          hdd.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          hdd.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          hdd.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          hdd.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "VGA":
          if (vga == null) {
            vga = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          vga.setProductId(p.getProduct_id());
          vga.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          vga.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          vga.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          vga.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "POWERSUPPLY":
          if (powerSupply == null) {
            powerSupply = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          powerSupply.setProductId(p.getProduct_id());
          powerSupply.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          powerSupply.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          powerSupply.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          powerSupply.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "CASES":
          if (cases == null) {
            cases = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          cases.setProductId(p.getProduct_id());
          cases.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          cases.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          cases.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          cases.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "COOLER":
          if (fan == null) {
            fan = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          fan.setProductId(p.getProduct_id());
          fan.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          fan.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          fan.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          fan.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "WATERCOOLING":
          if (waterCooling == null) {
            waterCooling = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          waterCooling.setProductId(p.getProduct_id());
          waterCooling.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          waterCooling.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          waterCooling.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          waterCooling.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "SCREEN":
          if (screen == null) {
            screen = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          screen.setProductId(p.getProduct_id());
          screen.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          screen.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          screen.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          screen.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "KEYBOARD":
          if (keyboard == null) {
            keyboard = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          keyboard.setProductId(p.getProduct_id());
          keyboard.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          keyboard.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          keyboard.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          keyboard.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "MOUSE":
          if (mouse == null) {
            mouse = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          mouse.setProductId(p.getProduct_id());
          mouse.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          mouse.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          mouse.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          mouse.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());

          break;
        case "EARPHONE":
          if (earphone == null) {
            earphone = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          earphone.setProductId(p.getProduct_id());
          earphone.setProduct_name(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_name());
          earphone.setProductType(
              productRepository.findProductByProductId(p.getProduct_id()).getProductType());

          earphone.setProductPrice(
              productRepository.findProductByProductId(p.getProduct_id()).getProductPrice());
          earphone.setProduct_image(
              productRepository.findProductByProductId(p.getProduct_id()).getProduct_image());
          break;
        default:
          System.out.println("Invalid product type: " + productType);
          break;
      }
    }
    int total = calculateTotal(componentList);
    model.addAttribute("total", total);
    int user_id = (int) session.getAttribute("user_id");
    model.addAttribute("user_id", user_id);
    model.addAttribute("componentList", componentList);
    model.addAttribute("pcBuildingService", pcBuildingService);
    model.addAttribute("pc_id", pc_id);
    model.addAttribute("productRepository", productRepository);

    model.addAttribute("cpu", cpu);
    model.addAttribute("main", main);
    model.addAttribute("ram", ram);
    model.addAttribute("ssd", ssd);
    model.addAttribute("hdd", hdd);
    model.addAttribute("vga", vga);
    model.addAttribute("powerSupply", powerSupply);
    model.addAttribute("cases", cases);
    model.addAttribute("fan", fan);
    model.addAttribute("waterCooling", waterCooling);
    model.addAttribute("screen", screen);
    model.addAttribute("earphone", earphone);
    model.addAttribute("keyboard", keyboard);
    session.setAttribute("componentList",componentList);

    return "build_pc_view_test";
  }


  // add component to list
  @GetMapping("/pc-building/add-product-to-list")
  public String addToList(@RequestParam("product_type") String product_type,
      @RequestParam("pc_id") int pc_id, Model model,
      @RequestParam("product_id") int product_id, HttpSession session) {

    System.out.println("list truoc khi add");
    for (PcComponent pcComponent : componentList) {
      System.out.println(pcComponent);
    }
    PcComponent p = new PcComponent();
    Product productInit = productRepository.findProductByProductId(product_id);
    p.setProduct_id(productInit.getProductId());
    p.setQuantity(1);
    p.setProduct_type(productInit.getProductType().trim());
    componentList.add(p);
//    System.out.println("pro duc ty pe: "+ p.getProduct_type());
    System.out.println("in ra list sau khi add");
    for (PcComponent pcComponent : componentList) {
      System.out.println(pcComponent);
    }
    System.out.println("------------------------------------");


    model.addAttribute("listComponent", componentList);

    int total = calculateTotal(componentList);
    model.addAttribute("total", total);
//
    model.addAttribute("componentList", componentList);
    session.setAttribute("componentList",componentList);


    model.addAttribute("pcBuildingService", pcBuildingService);
//    System.out.println("ADDED: " + p);

    return "build_pc_view_test";

  }

  @GetMapping("/pc-building/remove/{pc_id}/{product_id}")
  public String removeComponent(Model model, @PathVariable("product_id") int product_id,
      @PathVariable("pc_id") int pc_id,
      HttpSession session) {

    Product product = productRepository.findProductByProductId(product_id);

    // Xóa sản phẩm khỏi productList
    Product productToRemove = null;
    Iterator<Product> iterator = productList.iterator();
    while (iterator.hasNext()) {
      Product p = iterator.next();
      if (p.getProductId() == product_id) {
        System.out.println("Tim thay");

        iterator.remove(); // Xóa phần tử khỏi productList
        System.out.println("Da xoa");
        break; // Kết thúc vòng lặp sau khi xóa phần tử
      }
    }
    // Xóa sản phẩm khỏi componentList
    Iterator<PcComponent> componentIterator = componentList.iterator();
    while (componentIterator.hasNext()) {
      PcComponent pcComponent = componentIterator.next();
      if (pcComponent.getProduct_id() == product_id) {
        System.out.println("Tim thay trong componentList");
        componentIterator.remove(); // Xóa phần tử khỏi componentList
        System.out.println("Da xoa khoi componentList");
        break; // Kết thúc vòng lặp sau khi xóa phần tử
      }
    }

    if (product == null) {
      System.out.println("Da Xoa");
    }
    Product cpu = null;
    Product main = null;
    Product ram = null;
    Product ssd = null;
    Product hdd = null;
    Product vga = null;
    Product powerSupply = null;
    Product cases = null;
    Product fan = null;
    Product waterCooling = null;
    Product screen = null;
    Product keyboard = null;
    Product mouse = null;
    Product earphone = null;
    for (PcComponent pcComponent : componentList) {
      System.out.println("So luong cua san pham la: " + pcComponent.getQuantity());
      String productType = pcComponent.getProduct_type();
      switch (productType.toUpperCase().trim()) {
        case "CPU":

          if (cpu == null) {
            cpu = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          cpu.setProductId(pcComponent.getProduct_id());
          cpu.setProduct_name(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_name());
          cpu.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());
          cpu.setProductPrice(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductPrice());
          cpu.setProduct_image(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_image());
          break;
        case "MAIN":
          if (main == null) {
            main = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          main.setProductId(pcComponent.getProduct_id());
          main.setProduct_name(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_name());
          main.setProductPrice(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductPrice());
          main.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          main.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        case "RAM":
          if (ram == null) {
            ram = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          ram.setProductId(pcComponent.getProduct_id());
          ram.setProduct_name(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_name());
          ram.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          ram.setProductPrice(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductPrice());
          ram.setProduct_image(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_image());

          break;
        case "SSD":
          if (ssd == null) {
            ssd = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          ssd.setProductId(pcComponent.getProduct_id());
          ssd.setProduct_name(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_name());
          ssd.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          ssd.setProductPrice(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductPrice());
          ssd.setProduct_image(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_image());

          break;
        case "HDD":
          if (hdd == null) {
            hdd = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          hdd.setProductId(pcComponent.getProduct_id());
          hdd.setProduct_name(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_name());
          hdd.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          hdd.setProductPrice(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductPrice());
          hdd.setProduct_image(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_image());

          break;
        case "VGA":
          if (vga == null) {
            vga = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          vga.setProductId(pcComponent.getProduct_id());
          vga.setProduct_name(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_name());
          vga.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          vga.setProductPrice(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductPrice());
          vga.setProduct_image(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_image());

          break;
        case "POWERSUPPLY":
          if (powerSupply == null) {
            powerSupply = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          powerSupply.setProductId(pcComponent.getProduct_id());
          powerSupply.setProduct_name(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_name());
          powerSupply.setProductType(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductType());

          powerSupply.setProductPrice(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductPrice());
          powerSupply.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        case "CASES":
          if (cases == null) {
            cases = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          cases.setProductId(pcComponent.getProduct_id());
          cases.setProduct_name(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_name());
          cases.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          cases.setProductPrice(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductPrice());
          cases.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        case "COOLER":
          if (fan == null) {
            fan = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          fan.setProductId(pcComponent.getProduct_id());
          fan.setProduct_name(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_name());
          fan.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          fan.setProductPrice(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductPrice());
          fan.setProduct_image(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProduct_image());

          break;
        case "WATERCOOLING":
          if (waterCooling == null) {
            waterCooling = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          waterCooling.setProductId(pcComponent.getProduct_id());
          waterCooling.setProduct_name(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_name());
          waterCooling.setProductType(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductType());

          waterCooling.setProductPrice(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductPrice());
          waterCooling.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        case "SCREEN":
          if (screen == null) {
            screen = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          screen.setProductId(pcComponent.getProduct_id());
          screen.setProduct_name(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_name());
          screen.setProductType(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductType());

          screen.setProductPrice(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductPrice());
          screen.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        case "KEYBOARD":
          if (keyboard == null) {
            keyboard = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          keyboard.setProductId(pcComponent.getProduct_id());
          keyboard.setProduct_name(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_name());
          keyboard.setProductType(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductType());

          keyboard.setProductPrice(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductPrice());
          keyboard.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        case "MOUSE":
          if (mouse == null) {
            mouse = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          mouse.setProductId(pcComponent.getProduct_id());
          mouse.setProduct_name(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_name());
          mouse.setProductType(productRepository.findProductByProductId(pcComponent.getProduct_id())
              .getProductType());

          mouse.setProductPrice(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductPrice());
          mouse.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        case "EARPHONE":
          if (earphone == null) {
            earphone = new Product(); // Khởi tạo cpu nếu chưa được khởi tạo
          }
          earphone.setProductId(pcComponent.getProduct_id());
          earphone.setProduct_name(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_name());
          earphone.setProductType(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductType());

          earphone.setProductPrice(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProductPrice());
          earphone.setProduct_image(
              productRepository.findProductByProductId(pcComponent.getProduct_id())
                  .getProduct_image());

          break;
        default:
          System.out.println("Invalid product type: " + productType);
          break;
      }
    }

    int user_id = (int) session.getAttribute("user_id");
    int total = calculateTotal(componentList);
    model.addAttribute("total", total);
    model.addAttribute("user_id", user_id);
    model.addAttribute("productList", productList);
    model.addAttribute("componentList", componentList);
    model.addAttribute("pcBuildingService", pcBuildingService);
    model.addAttribute("pc_id", pc_id);
    model.addAttribute("productRepository", productRepository);
    model.addAttribute("cpu", cpu);
    model.addAttribute("main", main);
    model.addAttribute("ram", ram);
    model.addAttribute("ssd", ssd);
    model.addAttribute("hdd", hdd);
    model.addAttribute("vga", vga);
    model.addAttribute("powerSupply", powerSupply);
    model.addAttribute("cases", cases);
    model.addAttribute("fan", fan);
    model.addAttribute("waterCooling", waterCooling);
    model.addAttribute("screen", screen);
    model.addAttribute("earphone", earphone);
    model.addAttribute("keyboard", keyboard);
    session.setAttribute("componentList",componentList);
    return "build_pc_view_test";

  }

  @GetMapping("/pc-building/updateQuantity")
  public String updateQuantity(@RequestParam("product_id") int product_id, @RequestParam("quantity")
  int quantity) {
    for (PcComponent pcComponent : componentList) {
      if (pcComponent.getProduct_id() == product_id) {
        pcComponent.setQuantity(quantity);
      }
    }
//  System.out.println("In ra so luong san pham trong list");
//  for(PcComponent pcComponent: componentList)
//  {
//    System.out.println("So luong san pham "+ pcComponent);
//  }
//  System.out.println("ket thuc in ra so luong san pham trong list");
    calculateTotal(componentList);
    return "build_pc_view_test";
  }

  private int calculateTotal(List<PcComponent> componentList) {
    int total = 0;
    for (PcComponent pcComponent : componentList) {
      total += productRepository.findProductByProductId(pcComponent.getProduct_id())
          .getProductPrice()*pcComponent.getQuantity();
    }
    return total;
  }
}
