package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public interface RecipeService {
    Iterable<Recipe> giveRecipes();
    Recipe findById(Long id);
    void deleteById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand cmd);

    RecipeCommand findCommandById(long id);
}
