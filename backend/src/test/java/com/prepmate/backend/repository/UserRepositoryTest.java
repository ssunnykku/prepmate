package com.prepmate.backend.repository;

import com.prepmate.backend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    @Transactional
    void findByUserId() {
        // given
        User user = User.builder()
                .email("sun@gmail.com")
                .name("김선희")
                .password("1234").build();
        userRepository.save(user);

        // when
        User data = userRepository.findByUserId(user.getUserId());

        // then
        assertThat(data.getUserId()).isEqualTo(user.getUserId());
        assertThat(data.getEmail()).isEqualTo(user.getEmail());
        assertThat(data.getName()).isEqualTo(user.getName());
        assertThat(data.getPassword()).isEqualTo(user.getPassword());
    }
}