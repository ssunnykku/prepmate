package com.prepmate.backend.runner;

import com.prepmate.backend.domain.User;
import com.prepmate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(User.builder()
                .name("서니").email("sun@gmail.com")
                .password("1234").build());
    }
}