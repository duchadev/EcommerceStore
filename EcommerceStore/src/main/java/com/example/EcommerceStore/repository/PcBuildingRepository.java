package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.PcBuilding;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcBuildingRepository extends JpaRepository<PcBuilding,Integer> {
List<PcBuilding> findPcBuildingsByUserId( int user_id);
PcBuilding findPcBuildingByUserIdAndPcId(int user_id, int pc_id);
}
