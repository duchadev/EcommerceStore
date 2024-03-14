package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.entity.UserAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAddressRepository extends JpaRepository<UserAddress,Integer> {
  List<UserAddress> findUserAddressesByUserId(int user_id);
  @Query(value = "SELECT TOP 1 * FROM user_address ORDER BY address_id DESC", nativeQuery = true)
  UserAddress getUserAddress();

@Query(value = "SELECT TOP 1 * FROM user_address where user_id =:user_id  ORDER BY address_id",nativeQuery = true)
  UserAddress findUserAddressByUserIdOrderByAddressIdAsc(int user_id);
UserAddress findUserAddressByAddressId(int address_id);

}
