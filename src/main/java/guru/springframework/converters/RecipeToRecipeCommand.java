package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesConverter;
    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;

    public RecipeToRecipeCommand(NotesToNotesCommand notesConverter,
                                 CategoryToCategoryCommand categoryConverter,
                                 IngredientToIngredientCommand ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        final RecipeCommand cmd = new RecipeCommand();
        cmd.setId(recipe.getId());
        cmd.setDescription(recipe.getDescription());
        cmd.setCookTime(recipe.getCookTime());
        cmd.setPrepTime(recipe.getPrepTime());
        cmd.setServings(recipe.getServings());
        cmd.setDifficulty(recipe.getDifficulty());
        cmd.setUrl(recipe.getUrl());
        cmd.setImage(recipe.getImage());
        cmd.setSource(recipe.getSource());
        cmd.setDirections(recipe.getDirections());

        cmd.setNotes(notesConverter.convert(recipe.getNotes()));

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0) {
            recipe.getCategories()
                    .forEach(category -> cmd.getCategories().add(categoryConverter.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
            recipe.getIngredients()
                    .forEach(ingredient -> cmd.getIngredients().add(ingredientConverter.convert(ingredient)));
        }
        return cmd;
    }
}
