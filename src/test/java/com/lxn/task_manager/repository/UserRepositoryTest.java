package com.lxn.task_manager.repository;


import com.lxn.task_manager.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPasswordHash("123123123123123123");

        UserEntity savedUser = userRepository.save(user);
        Assertions.assertNotNull(savedUser.getUserId());
    }
}
