package com.example.expandedproject.member.service;

import com.example.expandedproject.member.model.EmailVerify;
import com.example.expandedproject.member.model.request.GetEmailConfirmReq;
import com.example.expandedproject.member.repository.EmailVerifyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailVerifyRepository emailVerifyRepository;

    public void create(String email, String token) {
        EmailVerify emailVerify = EmailVerify.builder()
                .email(email)
                .token(token)
                .build();
        emailVerifyRepository.save(emailVerify);
    }

    public Boolean verify(String email, String token) {
        Optional<EmailVerify> result = emailVerifyRepository.findByEmail(email);
        if(result.isPresent()){
            EmailVerify emailVerify = result.get();
            if(emailVerify.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }


}
