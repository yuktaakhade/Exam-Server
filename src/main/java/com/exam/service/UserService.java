package com.exam.service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    //creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //get user
    public User getUser(String username);

    //delete user by id
    public void deleteUser(Long userId);

    //update user by id
    public User updateUser(Long userId, User user);

}
