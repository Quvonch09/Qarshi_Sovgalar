package com.example.qarshi_sovgalar.service;

import com.example.qarshi_sovgalar.entity.User;
import com.example.qarshi_sovgalar.payload.ApiResponse;
import com.example.qarshi_sovgalar.payload.auth.ResponseLogin;
import com.example.qarshi_sovgalar.payload.res.ResUser;
import com.example.qarshi_sovgalar.repository.UserRepository;
import com.example.qarshi_sovgalar.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    public ApiResponse getMe(User user){
        ResUser resUser = ResUser.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .build();
        return new ApiResponse(resUser);
    }


    public ApiResponse updateMe(ResUser resUser, User user){
        if (!user.getPhoneNumber().equals(resUser.getPhoneNumber())){
            user.setPhoneNumber(resUser.getPhoneNumber());
            userRepository.save(user);
            String token = jwtProvider.generateToken(resUser.getPhoneNumber());
            ResponseLogin responseLogin = new ResponseLogin(token, user.getRole().name(), user.getId());
            return new ApiResponse(responseLogin);
        }

        user.setFullName(resUser.getFullName());
        userRepository.save(user);
        return new ApiResponse("Successfully updated");
    }
}
