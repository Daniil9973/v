package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request){
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);       
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUserAll(){
        List<UserResponse> userResponses = userService.getUserAll();
        return userResponses!=null ? ResponseEntity.status(HttpStatus.OK).body(userResponses)
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@Valid @PathVariable Long id){
        UserResponse userResponse = userService.getUserById(id);
        return userResponse!=null ? 
            ResponseEntity.status(HttpStatus.OK).body(userResponse)
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @PathVariable Long id, UserRequest request){
        UserResponse userResponse = userService.updateUser(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}