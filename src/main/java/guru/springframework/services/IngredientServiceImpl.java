package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand toCommandConverter;
    private final IngredientCommandToIngredient fromCommandConverter;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand toCommandConverter,
                                 IngredientCommandToIngredient fromCommandConverter, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.toCommandConverter = toCommandConverter;
        this.fromCommandConverter = fromCommandConverter;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findCommandById(long id) {
        return null;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            // todo implement error handling
            log.error("recipe id not found.  Id: " + recipeId);
        }
        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> toCommandConverter.convert(ingredient)).findFirst();
        if (!ingredientCommandOptional.isPresent()) {
            // todo implement error handling
            log.error("Ingredient id not found: " + ingredientId);
        }
        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand cmd) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(cmd.getRecipeId());

        if (!recipeOptional.isPresent()) {
            // todo toss error if not found
            log.error("Recipe not found for id: " + cmd.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe r = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = r
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(cmd.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                // ingredient already exists --> update
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(cmd.getDescription());
                ingredientFound.setAmount(cmd.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(cmd.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found"))); // todo handle missed UOM
            } else {
                // add new Ingredient
                Ingredient ingredient = fromCommandConverter.convert(cmd);
                ingredient.setRecipe(r);
                r.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(r);

            Optional<Ingredient> saveIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(cmd.getId()))
                    .findFirst();

            if (!saveIngredientOptional.isPresent()) {
                saveIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(cmd.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(cmd.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(cmd.getUom().getId()))
                        .findFirst();
            }
            return toCommandConverter.convert(saveIngredientOptional.get());
        }
    }
}
