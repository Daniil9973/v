package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional()
public class UserService {
    
    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest req){
        if(userRepository.existsByPhone(req.getPhone())){
            throw new IllegalArgumentException("Пользователь с таким номером телефона уже существует");
        }
        try{
            User user = new User(req.getName(), req.getAge(), req.getPhone(), req.getPassword());
            userRepository.save(user);

            return new UserResponse(user.getId(), user.getName(), 
                user.getAge(), user.getPhone());
            //Logs.info()
        } catch(Exception e){
            throw new RuntimeException("Ошибка на стороне сервера ", e);
            //Logs.error("", e)     
        }
    }

    public List<UserResponse> getUserAll(){
        try {
            
            List<User> users = userRepository.findAll();
            List<UserResponse> userResponses = users.stream()
                .map(u -> toResponse(u))
                .collect(Collectors.toList());
            return userResponses;
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка на стороне сервера ", e);
            //Logs.error("", e)

        }
    }

    public UserResponse getUserById(Long id){
        try{
            UserResponse userResponse = userRepository.findById(id).
                map(u-> new UserResponse(u.getId(), u.getName(), u.getAge(), u.getPhone())).
                orElse(null);
            return userResponse;
        }catch(Exception e){
            //Logs.error("", e)
            throw new RuntimeException("Ошибка на стороне сервера ", e);
        }
    }

    public UserResponse updateUser(Long id, UserRequest req){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Пользователя с id: " + id + " не существует"));

        if(!req.getPhone().isBlank() && !req.getPhone().equals(user.getPhone())){
            if(userRepository.existsByPhone(req.getPhone())){
                throw new IllegalArgumentException("Пользователь с таким номером телефона уже существует");
        
            }
        }

        user.setName(req.getName());
        user.setAge(req.getAge());
        user.setPhone(req.getPhone());
        user.setPassword(req.getPassword());   

        try{
            userRepository.save(user);
            return toResponse(user);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка на стороне сервера ", e);
        }   
    }

    
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new IllegalArgumentException("Пользователь с таким номером телефона не найден");
        }
        try{
            userRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException("Ошибка на стороне сервера ", e);
        }    
    }
    
    private UserResponse toResponse(User user){
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getPhone());
    }
    
    
}

// 1 к M; M to M; M to 1
// отдел 
// тспользовать jdbc teamplay свой класс подстановки значений из кофнига