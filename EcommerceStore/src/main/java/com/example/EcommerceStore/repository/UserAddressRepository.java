package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.entity.UserAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress,Integer> {
  List<UserAddress> findUserAddressesByUserId(int user_id);
}
