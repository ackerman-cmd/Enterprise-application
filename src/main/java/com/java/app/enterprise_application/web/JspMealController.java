package com.java.app.enterprise_application.web;


import com.java.app.enterprise_application.model.Meal;
import com.java.app.enterprise_application.utils.aspects.Log;
import com.java.app.enterprise_application.web.meal.MealRestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/meals")
@Log
public class JspMealController {


    private final MealRestController restController;

    public JspMealController(MealRestController restController) {
        this.restController = restController;
    }

    @GetMapping
    public String getMealsForUser(Model model) {
        model.addAttribute("meals", restController.getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String addMeal(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping()
    public String setMeal(@ModelAttribute Meal meal) {
        if (meal.isNew()) {
            restController.create(meal);
        } else {
            restController.update(meal, meal.id());
        }
        return "redirect:meals";
    }

    @GetMapping("/update")
    public String editMeal(@RequestParam("id") int id, Model model) {
        Meal forEdit = restController.get(id);
        model.addAttribute(forEdit);
        return "mealForm";
    }

    @GetMapping("/delete")
    public String deleteMeal(@RequestParam("id") int id) {
        restController.delete(id);
        return "redirect:/meals";
    }

}
