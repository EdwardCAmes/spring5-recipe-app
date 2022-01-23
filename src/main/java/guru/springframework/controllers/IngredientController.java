package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService oumService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService oumService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.oumService = oumService;
    }

    @GetMapping
    @RequestMapping("/recipe/{idStr}/ingredients")
    public String listIngredients(@PathVariable String idStr, Model model) {
        log.debug("Getting ingredient list for recipe id: " + idStr);
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(idStr)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeIdStr}/ingredient/{ingredientIdStr}/show")
    public String showRecipeIngredient(@PathVariable String recipeIdStr, @PathVariable String ingredientIdStr, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.parseLong(recipeIdStr), Long.parseLong(ingredientIdStr)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeIdStr}/ingredient/{ingredientIdStr}/update")
    public String updateRecipeIngredient(@PathVariable String recipeIdStr, @PathVariable String ingredientIdStr, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.parseLong(recipeIdStr), Long.parseLong(ingredientIdStr)));
        model.addAttribute("uomList", oumService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeIdStr}/ingredient")
    public String saveOrUpdate(@PathVariable String recipeIdStr, @ModelAttribute IngredientCommand ingredientCommand) {
        ingredientCommand.setRecipeId(Long.parseLong(recipeIdStr));
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug("saved recipe id:  " + savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id " + savedIngredientCommand.getId());
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeIdStr}/ingredient/new")
    public String newRecipeIngredient(@PathVariable String recipeIdStr, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.parseLong(recipeIdStr));
        // todo raise exception if null

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.parseLong(recipeIdStr));
        model.addAttribute("ingredient", ingredientCommand);

        // init UOM
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList", oumService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeIdStr}/ingredient/{ingredientIdStr}/delete")
    public String deleteIngredientFromRecipe(@PathVariable String recipeIdStr, @PathVariable String ingredientIdStr) {
        ingredientService.deleteById(Long.parseLong(recipeIdStr), Long.parseLong(ingredientIdStr));
        return "redirect:/recipe/" + recipeIdStr + "/ingredients";
    }
}
