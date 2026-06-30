package com.example.demo.services;


import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.models.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;

    public UserResponse createUser(String name, Integer age, String phone, String password){
        if(userRepository.existsByPhone(phone)){
            //throw new IllegalArgumentException("");
            return null;
        }
        try{
            User user = new User(name, age, phone, password);
            userRepository.save(user);
            return new UserResponse(user.getId(), user.getName(), 
            user.getAge(), user.getPhone());
            //Logs.info()
        } catch(Exception e){
            //throw new RuntimeException("", e)
            return null;
            //Logs.error("", e)     
        }
    }
    
    


}
