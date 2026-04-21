package com.dylan.backend.service;

import com.dylan.backend.dto.RegisterDTO;
import com.dylan.backend.dto.UserDTO;
import com.dylan.backend.dto.LoginDTO;
import com.dylan.backend.dto.LoginResponseDTO;
import com.dylan.backend.entity.User;
import com.dylan.backend.repository.UserRepository;
import com.dylan.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDTO register(RegisterDTO registerDTO){
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public LoginResponseDTO login(LoginDTO loginDTO){

        User user = userRepository.findByUsername(loginDTO.getUsername());

        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }

        // raw password then ahshedpassword
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return new LoginResponseDTO(jwtUtil.generateToken(user.getUsername()), user.getUserId());
    }

}
