package com.dylan.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.dylan.backend.dto.UserDTO;
import com.dylan.backend.dto.RegisterDTO;
import com.dylan.backend.dto.LoginDTO;
import com.dylan.backend.dto.LoginResponseDTO;
import com.dylan.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(userService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    
}
