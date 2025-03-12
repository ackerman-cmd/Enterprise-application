package com.java.app.enterprise_application.repository.datajpa;

import com.java.app.enterprise_application.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


}
