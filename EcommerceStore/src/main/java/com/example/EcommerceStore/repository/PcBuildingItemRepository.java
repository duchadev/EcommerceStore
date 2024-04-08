package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.PcBuilding;
import com.example.EcommerceStore.entity.PcBuildingItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PcBuildingItemRepository extends JpaRepository<PcBuildingItem, Integer> {
  @Query(value="select * from pc_building_item p where p.pc_building_id = :pc_building_id",
      nativeQuery = true)
List<PcBuildingItem> findPcBuildingItemsByPc_building_id(int pc_building_id);
  @Query(value="select * from pc_building_item p where p.product_id = :product_id "
      + "and p.pc_building_id = :pc_building_id", nativeQuery = true)
  PcBuildingItem findPcBuildingItemsByProduct_idAndPc_building_id(int product_id, int pc_building_id);

}
