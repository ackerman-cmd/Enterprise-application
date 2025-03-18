package com.java.app.enterprise_application.repository.datajpa;


import com.java.app.enterprise_application.model.Meal;
import com.java.app.enterprise_application.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository mealRepository;

    private final CrudUserRepository userRepository;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(userRepository.getReferenceById(userId));
            return mealRepository.save(meal);
        } else {
            Meal existingMeal = get(meal.id(), userId);
            if (existingMeal != null) {
                updateMeal(existingMeal, meal);
                return mealRepository.save(existingMeal);
            }
            return null;
        }
    }

    private void updateMeal(Meal existingMeal, Meal meal) {
        existingMeal.setDescription(meal.getDescription());
        existingMeal.setCalories(meal.getCalories());
        existingMeal.setDateTime(meal.getDateTime());
    }

    @Override
    public boolean delete(int id, int userId) {
        return get(id, userId) != null && mealRepository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) {
        return mealRepository.findById(id)
                .filter(meal -> meal.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return mealRepository.getAllForUserSorted(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return mealRepository.getBetweenHalfOpen(userId, startDateTime, endDateTime);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return mealRepository.getWithUser(id, userId);
    }
}
