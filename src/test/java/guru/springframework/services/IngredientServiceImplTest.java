package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private IngredientService ingredientService;
    IngredientToIngredientCommand toCommandConverter;
    IngredientCommandToIngredient fromCommandConverter;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        toCommandConverter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        fromCommandConverter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(recipeRepository, toCommandConverter, fromCommandConverter, unitOfMeasureRepository);
    }
    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() {
        // Given
        Recipe r = new Recipe();
        r.setId(1L);
        Ingredient i1 = new Ingredient();
        i1.setId(101L);
        r.addIngredient(i1);
        Ingredient i2 = new Ingredient();
        i2.setId(102L);
        r.addIngredient(i2);
        Ingredient i3 = new Ingredient();
        i3.setId(103L);
        r.addIngredient(i3);
        Optional<Recipe> recipeOptional = Optional.of(r);

        when( recipeRepository.findById( anyLong() )).thenReturn(recipeOptional);

        // When
        IngredientCommand actualCmd = ingredientService.findByRecipeIdAndIngredientId(1L, 103L);

        // THen
        assertEquals(Long.valueOf(103), actualCmd.getId());
        assertEquals(Long.valueOf(1), actualCmd.getRecipeId());
        verify(recipeRepository, times(1)).findById( anyLong() );
    }

    @Test
    public void testSaveRecipe() {
        // Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when( recipeRepository.findById( anyLong() ) ).thenReturn(recipeOptional);
        when( recipeRepository.save( any() )).thenReturn(savedRecipe);

        // When
        IngredientCommand actualIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        // Then
        assertEquals(Long.valueOf(3L), actualIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById( anyLong() );
        verify(recipeRepository, times(1)).save( any(Recipe.class) );
    }

    @Test
    public void testDeleteIngredient() {
        // Given
        Recipe r = new Recipe();
        r.setId(2L);
        Ingredient i = new Ingredient();
        i.setId(3L);
        r.addIngredient(i);
        i.setRecipe(r);
        Optional<Recipe> recipeOptional = Optional.of(r);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // When
        ingredientService.deleteById(2, 3);

        // Then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save( any(Recipe.class));
    }
}