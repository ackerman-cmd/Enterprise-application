package com.java.app.enterprise_application.repository.jpa;

import com.java.app.enterprise_application.model.Meal;
import com.java.app.enterprise_application.model.User;
import com.java.app.enterprise_application.repository.MealRepository;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    private final EntityManager entityManager;

    public JpaMealRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(entityManager.getReference(User.class, userId));

        if(meal.isNew()) {
            entityManager.persist(meal);
            return meal;
        }

        return get(meal.id(), userId) == null ? null: entityManager.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;

    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = entityManager.find(Meal.class, id);

        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return entityManager.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();

    }

}
