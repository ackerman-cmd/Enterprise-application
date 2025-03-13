package com.java.app.enterprise_application.repository.jpa;

import com.java.app.enterprise_application.SpringBootApplicationTest;
import com.java.app.enterprise_application.model.Role;
import com.java.app.enterprise_application.model.User;
import com.java.app.enterprise_application.repository.UserRepository;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JpaUserRepositoryTest extends SpringBootApplicationTest {

    private UserRepository userRepository;

    private User testUser;

    private User testAdmin;

    @Setter
    private int userId;

    @Setter
    private int adminId;

    public JpaUserRepositoryTest(@Qualifier("jpaUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        String uniqueEmailUser = "testUser" + System.currentTimeMillis() + "@mail.ru";
        String uniqueEmailAdmin = "testAdmin" + System.currentTimeMillis() + "@mail.ru";

        testUser = userRepository.save(new User( "Egor", uniqueEmailUser, "ackerman",Role.USER ));
        testAdmin = userRepository.save(new User( "nikita", uniqueEmailAdmin, "ackerman123", Role.ADMIN));
        setUserId(testUser.id());
        setAdminId(testAdmin.id());
    }

    @AfterEach
    void tearDown() {
        if (userId != 0) {
            userRepository.delete(testUser.id());
        }
        if (adminId != 0) {
            userRepository.delete(testAdmin.id());
        }
    }

    @Test
    void save() {
        // Test saving a new user
        User savedUser = userRepository.save(new User("John", "john@mail.ru", "ackerman", Role.USER));
        assertNotNull(savedUser);

        // Test saving an existing user
        testUser.setEmail("new-email@mail.ru");
        User updatedUser = userRepository.save(testUser);
        assertEquals("new-email@mail.ru", updatedUser.getEmail());
    }

    @Test
    void delete() {
        // Test deleting a user by ID
        assertTrue(userRepository.delete(userId));
        assertNull(userRepository.get(userId));

        // Test deleting a user that doesn't exist
        assertFalse(userRepository.delete(9999));
    }

    @Test
    void get() {
        // Test getting a user by ID
        User foundUser = userRepository.get(userId);
        assertNotNull(foundUser);
        assertEquals("Egor", foundUser.getName());

        // Test getting a user that doesn't exist
        User notFoundUser = userRepository.get(9999);
        assertNull(notFoundUser);
    }

    @Test
    void getByEmail() {
        // Test getting a user by email
        User foundUser = userRepository.getByEmail("user@yandex.ru");
        assertNotNull(foundUser);
        assertEquals("User", foundUser.getName());

        // Test getting a user by a non-existent email
        User notFoundUser = userRepository.getByEmail("nonexistent@mail.ru");
        assertNull(notFoundUser);
    }

    @Test
    void getAll() {
        // Test getting all users
        List<User> users = userRepository.getAll();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }
}
