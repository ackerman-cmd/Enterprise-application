package com.java.app.enterprise_application.repository.jpa;


import com.java.app.enterprise_application.model.Meal;
import com.java.app.enterprise_application.model.User;
import com.java.app.enterprise_application.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        }
        return em.merge(user);
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(User.DELETE)
                .setParameter("user_id", id)
                .executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter("email", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class)
                .getResultList();
    }
}
