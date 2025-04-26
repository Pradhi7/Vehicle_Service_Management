package com.examly.springapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{
    
    @Query("SELECT u FROM User u WHERE u.username Like :name")
    Optional<User> getUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.username Like :username")
    Optional<User> findByUserName(String username);

    @Query("SELECT u.userRole FROM User u WHERE u.username Like :username")
    public String findUserRoleByUsername(String username);

    @Query("SELECT u.userId FROM User u WHERE u.username Like :username")
    public Integer findUserIdByUsername(String username);

    // Check if username exists
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    // Check if email exists
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    // Check if mobileNumber exists
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.mobileNumber = :mobileNumber")
    boolean existsByMobileNumber(@Param("mobileNumber") String mobileNumber);

}
