package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandConverter;
    private final RecipeToRecipeCommand recipeConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandConverter,
                             RecipeToRecipeCommand recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandConverter = recipeCommandConverter;
        this.recipeConverter = recipeConverter;
    }

    @Override
    public Set<Recipe> giveRecipes() {
        log.debug("In Recipe SERVICE");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }
    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found");
        }
        return recipeOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand cmd) {
        Recipe detachedRecipe = recipeCommandConverter.convert(cmd);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe id = " + detachedRecipe.getId());
        return recipeConverter.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found");
        }
        RecipeCommand cmd = recipeConverter.convert(recipeOptional.get());
        return cmd;
    }
}
