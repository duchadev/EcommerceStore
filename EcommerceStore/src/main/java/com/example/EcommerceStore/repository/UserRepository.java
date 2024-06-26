package com.example.EcommerceStore.repository;

import com.example.EcommerceStore.entity.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUserEmail(String emailId);
  User findUserByUserId(int user_id);
  @Query(value = "select * from dbo_user where user_email = :user_email", nativeQuery = true)
  Optional<User> findByEmail(@Param("user_email") String user_email);

  @Modifying
  @Query("UPDATE User SET user_name = :user_name, user_phoneNumber = :user_phoneNumber, "
      + "birthday = :birthday WHERE userId = :user_id")
  void updateUserByUserId(@Param("user_id") int user_id,
      @Param("user_name") String user_name,
      @Param("user_phoneNumber") String user_phoneNumber,
      @Param("birthday") Date birthday);


  @Query("SELECT u FROM User u WHERE u.user_name = :username")
  public User getUserByUsername(@Param("username") String username);

  boolean existsUserByUserEmail(String userEmail);

  boolean existsByPassword(String password);
  User findUserByUserEmail(String email);
}
