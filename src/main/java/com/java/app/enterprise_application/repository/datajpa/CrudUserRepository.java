package com.java.app.enterprise_application.repository.datajpa;

import com.java.app.enterprise_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
}
