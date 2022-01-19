package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
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

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(
                new NotesToNotesCommand(),
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    public void testNull() {  assertNull(converter.convert(null));  }
    @Test
    public void testEmptyRecipe() { assertNotNull( converter.convert(new Recipe())); }
    @Test
    public void convert() {
        // Given
        Recipe r = new Recipe();
        r.setId(ID);
        r.setDescription(DESCRIPTION);
        r.setPrepTime(PREP_TIME);
        r.setCookTime(COOK_TIME);
        r.setServings(SERVINGS);
        r.setDifficulty(DIFFICULTY);
        r.setSource(SOURCE);
        r.setUrl(URL);
        r.setDirections(DIRECTIONS);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        r.setNotes(notes);

        Set<Category> cats = new HashSet<>();
        Category cat1 = new Category();
        cat1.setId(CAT_ID_1);
        cats.add(cat1);
        Category cat2 = new Category();
        cat2.setId(CAT_ID_2);
        cats.add(cat2);
        r.setCategories(cats);

        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID_1);
        ingredients.add(ingredient1);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID_2);
        ingredients.add(ingredient2);
        r.setIngredients(ingredients);

        // When
        RecipeCommand cmd = converter.convert(r);

        // Then
        assertNotNull(cmd);
        assertEquals(ID, cmd.getId());
        assertEquals(DESCRIPTION, cmd.getDescription());
        assertEquals(PREP_TIME, cmd.getPrepTime());
        assertEquals(COOK_TIME, cmd.getCookTime());
        assertEquals(SERVINGS, cmd.getServings());
        assertEquals(DIFFICULTY, cmd.getDifficulty());
        assertEquals(SOURCE, cmd.getSource());
        assertEquals(URL, cmd.getUrl());
        assertEquals(DIRECTIONS, cmd.getDirections());

        assertNotNull(cmd.getNotes());
        assertEquals(NOTES_ID, cmd.getNotes().getId());

        assertNotNull(cmd.getCategories());
        assertEquals(2, cmd.getCategories().size());

        assertNotNull(cmd.getIngredients());
        assertEquals(2, cmd.getIngredients().size());
    }
}