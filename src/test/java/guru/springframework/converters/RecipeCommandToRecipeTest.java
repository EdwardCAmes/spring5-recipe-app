package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
    private static final Long ID = 100L;
    private static final String DESCRIPTION = "foo bar";
    private static final Integer PREP_TIME = 5667;
    private static final Integer COOK_TIME = 67554;
    private static final Integer SERVINGS = 435;
    private static final Difficulty DIFFICULTY = Difficulty.MODERATE;
    private static final String SOURCE = "a place";
    private static final String URL = "nowhere";
    private static final String DIRECTIONS = "Just do it!";
    private static final Long NOTES_ID = ID + 200;
    private static final Long CAT_ID_1 = ID + 300 + 1;
    private static final Long CAT_ID_2 = ID + 300 + 2;
    private static final Long INGREDIENT_ID_1 = ID + 400 + 1;
    private static final Long INGREDIENT_ID_2 = ID + 400 + 2;

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(
                new NotesCommandToNotes(),
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test public void testNull() { assertNull( converter.convert(null)); }
    @Test public void testEmptyRecipeCommand() { assertNotNull( converter.convert( new RecipeCommand())); }

    @Test
    public void convert() {
        // Given
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);
        cmd.setPrepTime(PREP_TIME);
        cmd.setCookTime(COOK_TIME);
        cmd.setServings(SERVINGS);
        cmd.setDifficulty(DIFFICULTY);
        cmd.setSource(SOURCE);
        cmd.setUrl(URL);
        cmd.setDirections(DIRECTIONS);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        cmd.setNotes(notes);

        Set<CategoryCommand> cats = new HashSet<>();
        CategoryCommand cat1 = new CategoryCommand();
        cat1.setId(CAT_ID_1);
        cats.add(cat1);
        CategoryCommand cat2 = new CategoryCommand();
        cat2.setId(CAT_ID_2);
        cats.add(cat2);
        cmd.setCategories(cats);

        Set<IngredientCommand> ingredients = new HashSet<>();
        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(INGREDIENT_ID_1);
        ingredients.add(ingredient1);
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGREDIENT_ID_2);
        ingredients.add(ingredient2);
        cmd.setIngredients(ingredients);

        // When
        Recipe r = converter.convert(cmd);

        // Then
        assertNotNull(cmd);
        assertEquals(ID, r.getId());
        assertEquals(DESCRIPTION, r.getDescription());
        assertEquals(PREP_TIME, r.getPrepTime());
        assertEquals(COOK_TIME, r.getCookTime());
        assertEquals(SERVINGS, r.getServings());
        assertEquals(DIFFICULTY, r.getDifficulty());
        assertEquals(SOURCE, r.getSource());
        assertEquals(URL, r.getUrl());
        assertEquals(DIRECTIONS, r.getDirections());

        assertNotNull(r.getNotes());
        assertEquals(NOTES_ID, r.getNotes().getId());

        assertNotNull(r.getCategories());
        assertEquals(2, r.getCategories().size());

        assertNotNull(r.getIngredients());
        assertEquals(2, r.getIngredients().size());
    }
}