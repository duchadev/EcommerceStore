package com.example.EcommerceStore.service;

import com.example.EcommerceStore.entity.PcComponent;
import java.util.List;

public interface PcBuildingService {
 void savePcBuilding(int user_id, int pc_id);
 void savePcComponents(int user_id, int pc_id, List<String> product_ids, int pc_building_id);

 void increaseQuantity(int product_id,int pc_building_id,int quantity, int pc_building_item_id);
 void decreaseQuantity(int product_id,int pc_building_id,int quantity, int pc_building_item_id);
void removePcBuildingItem( int pc_building_item_id);
void savePcComponent(int product_id, int quantity, int pc_building_id);
PcComponent findPcComponentByProductId(List<PcComponent> componentList, int product_id);
}
