package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    @Mock
    private RecipeService recipeService;

    private IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService);
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
}
