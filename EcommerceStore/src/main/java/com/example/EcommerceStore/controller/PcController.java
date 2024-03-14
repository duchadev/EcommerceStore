package com.example.EcommerceStore.controller;

import com.example.EcommerceStore.entity.PC;
import com.example.EcommerceStore.entity.Product;
import com.example.EcommerceStore.repository.PcRepository;
import com.example.EcommerceStore.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/EcommerceStore")
public class PcController {
  @Autowired
  private PcRepository pcRepository;
  @Autowired
  private ProductRepository productRepository;
@GetMapping("/build-pc")
  public String getMapping(Model model)
{
  List<PC> pcList = pcRepository.findAll();
  model.addAttribute("pcList",pcList);
  return "build_pc";
}
@GetMapping("/build-pc-view")
  public String view(HttpServletRequest request, Model model)
{
int pc_id = Integer.parseInt(request.getParameter("pc_id"));
List<Product> products = productRepository.findProductByPc_id(pc_id);
model.addAttribute("productList",products);
return "build_pc_view";
}

}
