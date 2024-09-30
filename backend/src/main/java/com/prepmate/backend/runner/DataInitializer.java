package com.prepmate.backend.runner;

import com.prepmate.backend.domain.User;
import com.prepmate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByName("서니") == null) {
            userRepository.save(User.builder()
                    .name("서니").email("sun@gmail.com")
                    .password("1234").
                    build());
        }
        log.info("userId= {}", userRepository.findByName("서니").getUserId());
    }
}