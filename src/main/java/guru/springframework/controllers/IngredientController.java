package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{idStr}/ingredients")
    public String listIngredients(@PathVariable String idStr, Model model) {
        log.debug("Getting ingredient list for recipe id: " + idStr);
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(idStr)));
        return "recipe/ingredient/list";
    }
}
