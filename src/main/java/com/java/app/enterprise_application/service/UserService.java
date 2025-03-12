package com.java.app.enterprise_application.service;


import com.java.app.enterprise_application.model.User;
import com.java.app.enterprise_application.repository.UserRepository;
import com.java.app.enterprise_application.utils.aspects.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.java.app.enterprise_application.utils.ValidationUtil.checkNotFound;

import java.util.List;

@Service
@Log
public class UserService {

    private final UserRepository userRepository;

    public UserService(@Qualifier("jpaUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFound(userRepository.get(id), id);
    }


    public User get(int id) {
        return checkNotFound(userRepository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }
    @Cacheable("users")
    public List<User> getAll() {
        return userRepository.getAll();

    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        userRepository.save(user);
    }

    public User getWithMeals(int id) {
        return checkNotFound(userRepository.getWithMeals(id), id);

    }

}
