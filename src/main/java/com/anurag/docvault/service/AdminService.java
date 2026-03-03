package com.example.docvault.service;

import com.example.docvault.dto.AppDTOs;
import com.example.docvault.entity.User;
import com.example.docvault.exception.AppException;
import com.example.docvault.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<AppDTOs.UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AppDTOs.UserDTO updateUser(String id, AppDTOs.UpdateUserRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found"));
        if (req.getName()  != null) user.setName(req.getName());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getRole()  != null) user.setRole(req.getRole());
        return toDTO(userRepository.save(user));
    }

    private AppDTOs.UserDTO toDTO(User u) {
        return new AppDTOs.UserDTO(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
}