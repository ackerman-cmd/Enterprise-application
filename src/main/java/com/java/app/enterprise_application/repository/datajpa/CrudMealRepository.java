package com.java.app.enterprise_application.repository.datajpa;

import com.java.app.enterprise_application.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.user.id = :user_id AND m.id = :id")
    boolean delete(@Param("user_id") int user_id, @Param("id") int id);

    @Query("SELECT Meal m FROM Meal  WHERE m.user.id = :user_id AND m.id = :id")
    Meal get(@Param("user_id") int user_id, @Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.user.id = :user_id ORDER BY m.dateTime DESC ")
    List<Meal> getAllForUserSorted(@Param("user_id") int user_id);


    @Query("SELECT m FROM Meal m WHERE m.user.id = :user_id AND m.dateTime >= :startTime AND m.dateTime < :endTime")
    List<Meal> getBetweenHalfOpen(@Param("user_id") int user_id, @Param("startTime") LocalDateTime start, @Param("endTime") LocalDateTime end);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = ?1 and m.user.id = ?2")
    Meal getWithUser(int id, int userId);
}
