package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.Address;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address,Integer> {

  @Query(value = "SELECT TOP 1 * FROM user_address ORDER BY address_id DESC", nativeQuery = true)
  Address getAddress();



  Address getAddressByAddressID(int userAddress);
  @Query(value = "select * from address where user_id = :userID", nativeQuery = true)
  ArrayList<Address> findByUser(@Param("userID") int userID);
  @Query(value = "SELECT * FROM address ORDER BY addressID DESC LIMIT 1", nativeQuery = true)
  Address getNewestAddress();

}
