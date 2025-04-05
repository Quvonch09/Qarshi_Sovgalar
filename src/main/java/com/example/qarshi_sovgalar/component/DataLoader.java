package com.example.qarshi_sovgalar.component;

import com.example.qarshi_sovgalar.entity.User;
import com.example.qarshi_sovgalar.entity.enums.Role;
import com.example.qarshi_sovgalar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args)  {
        if (ddl.equals("create-drop") || ddl.equals("create")) {
            User newUser = new User();
            newUser.setFullName("Admin admin");
            newUser.setPassword(passwordEncoder.encode("12345"));
            newUser.setRole(Role.ROLE_ADMIN);
            newUser.setPhoneNumber("998901234567");
            newUser.setEnabled(true);
            userRepository.save(newUser);
        }
    }
}
