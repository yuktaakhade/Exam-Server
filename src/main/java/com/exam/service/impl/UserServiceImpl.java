package com.exam.service.impl;

import com.exam.exception.UserFoundExcpetion;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    //creating user
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {

        User local = this.userRepository.findByUsername(user.getUsername());
        if(local != null){
            System.out.println("User is already there!!!");
            throw new UserFoundExcpetion("User Already Present!!!");
        }else {
            //user create
            for(UserRole ur: userRoles){
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }
        return local;
    }

    //get user
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    //delete user
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    //update user
    @Override
    public User updateUser(Long userId, User user) {
        User newUserDetails = new User();
        newUserDetails.setId(userId);
        newUserDetails.setUsername(user.getUsername());
        newUserDetails.setFirstName(user.getFirstName());
        newUserDetails.setLastName(user.getLastName());
        newUserDetails.setPassword(user.getPassword());
        newUserDetails.setEmail(user.getEmail());
        newUserDetails.setPhone(user.getPhone());
        newUserDetails.setProfile(user.getProfile());
        this.userRepository.save(newUserDetails);
        return newUserDetails;
    }


}
