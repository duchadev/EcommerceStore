package com.example.EcommerceStore.service.impl;

import com.example.EcommerceStore.entity.PcBuilding;
import com.example.EcommerceStore.entity.PcBuildingItem;
import com.example.EcommerceStore.entity.PcComponent;
import com.example.EcommerceStore.repository.PcBuildingItemRepository;
import com.example.EcommerceStore.repository.PcBuildingRepository;
import com.example.EcommerceStore.service.PcBuildingService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PcBuildingServiceImpl implements PcBuildingService {

  @Autowired
  PcBuildingRepository pcBuildingRepository;
  @Autowired
  PcBuildingItemRepository pcBuildingItemRepository;

  @Override
  public void savePcBuilding(int user_id, int pc_id) {
    List<PcBuilding> pcBuildingList = pcBuildingRepository.findPcBuildingsByUserId(user_id);
    int count = 0;
    for (PcBuilding p : pcBuildingList) {
      if ((pc_id == p.getPcId())) {
        count += 1;
      }
    }
    if (count == 0) {
      PcBuilding pcBuilding = new PcBuilding();
      pcBuilding.setUserId(user_id);
      pcBuilding.setPcId(pc_id);
      pcBuildingRepository.save(pcBuilding);
    }
  }

  @Override
  public void savePcComponents(int user_id, int pc_id, List<String> product_ids
      , int pc_building_id) {
    PcBuilding pcBuilding = pcBuildingRepository.findPcBuildingByUserIdAndPcId(user_id, pc_id);
    List<PcBuildingItem> pcBuildingItemList = pcBuildingItemRepository.findAll();
    int count = 0;
    for (String product_id : product_ids) {
      for (PcBuildingItem p : pcBuildingItemList) {
        if ((Integer.parseInt(product_id) == (p.getProduct_id()))
            && p.getPc_building_id() == pc_building_id) {
          count += 1;
        }
      }
      if (count == 0) {
        System.out.println(product_id);
        PcBuildingItem pcBuildingItem = new PcBuildingItem();
        pcBuildingItem.setProduct_id(Integer.parseInt(product_id));
        pcBuildingItem.setQuantity(1);
        pcBuildingItem.setPc_building_id(pcBuilding.getPc_building_id());
        pcBuilding.getPcBuildingItems().add(pcBuildingItem);
        pcBuildingItemRepository.save(pcBuildingItem);
      }

    }
  }

  @Override
  public void increaseQuantity(int product_id, int pc_building_id, int quantity,
      int pc_building_item_id) {
    PcBuildingItem pcBuildingItem = pcBuildingItemRepository.getReferenceById(
        pc_building_item_id);
    quantity += 1;
    pcBuildingItem.setQuantity(quantity);
    pcBuildingItem.setProduct_id(product_id);
    pcBuildingItem.setPc_building_id(pc_building_id);
    pcBuildingItemRepository.save(pcBuildingItem);
  }

  @Override
  public void decreaseQuantity(int product_id, int pc_building_id, int quantity,
      int pc_building_item_id) {
    PcBuildingItem pcBuildingItem = pcBuildingItemRepository.getReferenceById(
        pc_building_item_id);
    if (quantity > 1) {
      quantity -= 1;
      pcBuildingItem.setQuantity(quantity);
      pcBuildingItem.setProduct_id(product_id);
      pcBuildingItem.setPc_building_id(pc_building_id);
      pcBuildingItemRepository.save(pcBuildingItem);
    }
  }

  @Override
  public void removePcBuildingItem(int pc_building_item_id) {
    try{
      PcBuildingItem pcBuildingItem = pcBuildingItemRepository.findById(pc_building_item_id).get();
      pcBuildingItemRepository.delete(pcBuildingItem);
    } catch (Exception exception)
    {
      exception.printStackTrace();
      exception.getMessage();
    }

  }

  @Override
  public void savePcComponent(int product_id, int quantity, int pc_building_id) {
    try{
      List<PcBuildingItem> pcBuildingItemList =
          pcBuildingItemRepository.findPcBuildingItemsByPc_building_id(pc_building_id);
      int count  =0;
      for(PcBuildingItem p: pcBuildingItemList)
      {
        if(product_id == p.getProduct_id())
        {
          count+=1;
        }
      }
      // Nếu chưa có thì tạo mới, có rồi thì +1 quantity
      if(count == 0)
      {
        PcBuildingItem pcBuildingItem = new PcBuildingItem();
        pcBuildingItem.setPc_building_id(pc_building_id);
         pcBuildingItem.setProduct_id(product_id);
        pcBuildingItem.setQuantity(quantity);
        pcBuildingItemRepository.save(pcBuildingItem);
      } else
      {
        PcBuildingItem pcBuildingItem =
            pcBuildingItemRepository.findPcBuildingItemsByProduct_idAndPc_building_id(product_id,pc_building_id);
        pcBuildingItem.setQuantity(pcBuildingItem.getQuantity()+1);
        pcBuildingItemRepository.save(pcBuildingItem);
      }

    } catch(Exception exception)
    {
      exception.printStackTrace();
      exception.getMessage();
    }

  }

  @Override
  public PcComponent findPcComponentByProductId(List<PcComponent> componentList, int product_id) {
    for(PcComponent pcComponent: componentList)
    {
      if(product_id == pcComponent.getProduct_id())
      {
        return pcComponent;
      }
    }
    return null;
  }


}
