package guru.springframework.services;

import guru.springframework.converters.*;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,
                new RecipeCommandToRecipe(
                        new NotesCommandToNotes(),
                        new CategoryCommandToCategory(),
                        new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure())),
                new RecipeToRecipeCommand(
                        new NotesToNotesCommand(),
                        new CategoryToCategoryCommand(),
                        new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand())));
    }

    @Test
    public void testGiveRecipeById() {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(100L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById( any() )).thenReturn(recipeOptional);

        // When
        Recipe actualRecipe = recipeService.findById(100L);

        // Then
        assertNotNull(actualRecipe);
        verify(recipeRepository).findById( anyLong() );
        verify(recipeRepository, never()).findAll();
    }
    @Test
    public void testGiveRecipes() {
        // Given
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        // When
        Set<Recipe> recipes = recipeService.giveRecipes();

        // Then
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById( anyLong() );
    }
}