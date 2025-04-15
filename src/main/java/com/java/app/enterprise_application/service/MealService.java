package com.java.app.enterprise_application.service;


import com.java.app.enterprise_application.model.Meal;
import com.java.app.enterprise_application.repository.MealRepository;
import com.java.app.enterprise_application.utils.aspects.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.java.app.enterprise_application.utils.DateTimeUtil.atStartOfDayOrMin;
import static com.java.app.enterprise_application.utils.DateTimeUtil.atStartOfNextDayOrMax;
import static com.java.app.enterprise_application.utils.ValidationUtil.*;

@Service
@Log
public class MealService {

    private final MealRepository mealRepository;

    public MealService(@Qualifier("dataJpaMealRepository") MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal create(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        return mealRepository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFound(mealRepository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFound(mealRepository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return mealRepository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        mealRepository.save(meal, userId);
    }

    public List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return mealRepository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }

    public Meal getWithUser(int id, int userId) {
        return checkNotFound(mealRepository.getWithUser(id, userId), id);
    }

}
