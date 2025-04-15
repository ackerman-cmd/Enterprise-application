package com.java.app.enterprise_application.web.meal;

import com.java.app.enterprise_application.model.Meal;
import com.java.app.enterprise_application.service.MealService;
import com.java.app.enterprise_application.to.MealTo;
import com.java.app.enterprise_application.utils.MealsUtil;
import com.java.app.enterprise_application.utils.aspects.Log;
import com.java.app.enterprise_application.web.SecurityUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.java.app.enterprise_application.utils.ValidationUtil.assureIdConsistent;
import static com.java.app.enterprise_application.utils.ValidationUtil.checkIsNew;


@Controller
@Log
public class MealRestController {

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        checkIsNew(meal);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}