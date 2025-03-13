package com.java.app.enterprise_application.repository.jpa;

import com.java.app.enterprise_application.SpringBootApplicationTest;
import com.java.app.enterprise_application.model.Meal;
import com.java.app.enterprise_application.repository.MealRepository;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JpaMealRepositoryTest extends SpringBootApplicationTest {

    @Autowired
    private MealRepository mealRepository;

    private static final int USER_ID = 100000;
    private static final int ADMIN_ID = 100001;

    private Meal mealUser;
    private Meal mealAdmin;

    @Setter
    private int mealUserDeleteId;
    @Setter
    private int mealAdminDeleteId;
    @Setter
    private LocalDateTime start;
    @Setter
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        mealUser = mealRepository.save(new Meal(LocalDateTime.now(), "testMeal", 200), USER_ID);
        mealAdmin = mealRepository.save(new Meal(LocalDateTime.now().plusDays(1), "testMeal", 400), ADMIN_ID);
        setMealUserDeleteId(mealUser.id());
        setMealAdminDeleteId(mealAdmin.id());
        setStart(mealUser.getDateTime().minusMonths(1));
        setEnd(mealAdmin.getDateTime().plusMonths(1));
    }

    @AfterEach
    void tearDown() {
        if (mealUserDeleteId != 0) {
            mealRepository.delete(mealUserDeleteId, USER_ID);
        }
        if (mealAdminDeleteId != 0) {
            mealRepository.delete(mealAdminDeleteId, ADMIN_ID);
        }
    }

    @Test
    void assertTrueFillTheTables() {
        List<Meal> meals = mealRepository.getAll(USER_ID);
        List<Meal> meals1 = mealRepository.getAll(ADMIN_ID);
        assertFalse(meals.isEmpty());
        assertFalse(meals1.isEmpty());
    }

    @Transactional
    @Test
    void save() {
        Meal savedUserMeal = mealRepository.save(new Meal(LocalDateTime.now(), "saved", 1000), USER_ID);
        Meal savedAdminMeal = mealRepository.save(new Meal(LocalDateTime.now().plusDays(1), "saved", 2000), ADMIN_ID);
        assertNotNull(savedUserMeal);
        assertNotNull(savedAdminMeal);
        setMealUserDeleteId(savedUserMeal.id());
        setMealAdminDeleteId(savedAdminMeal.id());
        setStart(savedUserMeal.getDateTime().minusMonths(1));
        setEnd(savedAdminMeal.getDateTime().plusMonths(1));
    }

    @Transactional
    @Test
    void delete() {
        assertNotNull(mealUserDeleteId, "User meal ID should not be null");
        assertNotNull(mealAdminDeleteId, "Admin meal ID should not be null");
        assertTrue(mealRepository.delete(mealAdminDeleteId, ADMIN_ID));
        assertTrue(mealRepository.delete(mealUserDeleteId, USER_ID));
    }

    @Test
    void get() {
        assertNotNull(mealRepository.get(mealUser.id(), USER_ID));
        assertNotNull(mealRepository.get(mealAdmin.id(), ADMIN_ID));
    }

    @Transactional
    @Test
    void getBetweenHalfOpen() {
        assertNotNull(start, "Start time should not be null");
        assertNotNull(end, "End time should not be null");

        assertEquals(1, mealRepository.getBetweenHalfOpen(start, end, USER_ID).size());
        assertEquals(1, mealRepository.getBetweenHalfOpen(start, end, ADMIN_ID).size());
    }
}
