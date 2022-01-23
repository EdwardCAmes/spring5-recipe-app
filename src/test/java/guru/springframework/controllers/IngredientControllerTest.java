package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    @Mock
    private RecipeService recipeService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private UnitOfMeasureService oumService;

    private IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService, ingredientService, oumService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        // Given
        RecipeCommand cmd = new RecipeCommand();
        when(recipeService.findCommandById( anyLong() )).thenReturn(cmd);

        // When
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect( status().isOk() )
                .andExpect( view().name("recipe/ingredient/list") )
                .andExpect( model().attributeExists("recipe"));

        // Then
        verify( recipeService, times(1)).findCommandById( anyLong() );
    }

    @Test
    public void testShowIngredient() throws Exception {
        // Given
        IngredientCommand cmd = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId( anyLong(), anyLong() )).thenReturn(cmd);

        // When
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect( status().isOk() )
                .andExpect( view().name("recipe/ingredient/show") )
                .andExpect( model().attributeExists("ingredient"));

        // Then
        verify( ingredientService, times(1)).findByRecipeIdAndIngredientId( anyLong(), anyLong() );
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        // Given
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(1L);

        when( recipeService.findCommandById( anyLong() ) ).thenReturn(cmd);
        when( oumService.listAllUoms()).thenReturn(new HashSet<>());

        // When
        mockMvc.perform( get("/recipe/1/ingredient/new"))
                .andExpect( status().isOk() )
                .andExpect( view().name("recipe/ingredient/ingredientform"))
                .andExpect( model().attributeExists("ingredient"))
                .andExpect( model().attributeExists("uomList"));

        // Then
        verify( recipeService, times(1)).findCommandById( anyLong() );
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        // Given
        IngredientCommand cmd = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId( anyLong(), anyLong() )).thenReturn(cmd);
        when(oumService.listAllUoms()).thenReturn(new HashSet<>());

        // When
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect( status().isOk() )
                .andExpect( view().name("recipe/ingredient/ingredientform") )
                .andExpect( model().attributeExists("ingredient"))
                .andExpect( model().attributeExists("uomList"));

        // Then
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        // Given
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(3L);
        cmd.setRecipeId(2L);

        when( ingredientService.saveIngredientCommand( any() ) ).thenReturn(cmd);

        // When
        // Then
        mockMvc.perform(
                post("/recipe/2/ingredient")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        // Given

        // When
        mockMvc.perform( get("/recipe/1/ingredient/2/delete"))
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/recipe/1/ingredients") );
        // Then
        verify(ingredientService, times(1)).deleteById( anyLong(), anyLong() );
    }
}
