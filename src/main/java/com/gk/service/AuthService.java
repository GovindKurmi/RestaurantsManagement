package com.gk.service;

import com.gk.model.Role;
import com.gk.model.User;
import com.gk.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registration(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = new Role();
        role.setName("USER");
        user.setRoles(List.of(role));
        userRepository.save(user);
    }
}
