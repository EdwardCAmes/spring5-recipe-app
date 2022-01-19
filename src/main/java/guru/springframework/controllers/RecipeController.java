package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{idStr}/show")
    public String showById(@PathVariable String idStr, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(idStr)));
        return "recipe/show";
    }
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }
    @RequestMapping("/recipe/{idStr}/update")
    public String updateRecipe(@PathVariable String idStr, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(idStr)));
        return "recipe/recipeform";
    }
    // POST return of above newRecipe() form return --> save to DB, then show page with that new recipe from DB
//    @RequestMapping("recipe", method= RequestMethod.POST)
    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand cmd) {
        RecipeCommand savedCmd = recipeService.saveRecipeCommand(cmd);
        return "redirect:/recipe/" + savedCmd.getId() + "/show/";
    }
}
