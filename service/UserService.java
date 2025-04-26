package com.examly.springapp.service;

import com.examly.springapp.model.User;
import java.util.*;

public interface UserService {
    public User loadUserByUsername(String userName);
    public List<User> findAllUsers();
    public User getByUserId(int userId);
    public void deleteUser(int userId);
    public User updateUser(int userId,User user);
    public Optional<User> getUserByName(String name);
    public User registerUser(User user);
}
