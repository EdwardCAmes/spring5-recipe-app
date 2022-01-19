package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    RecipeController controller;
    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetRecipe() throws Exception {
        // Given
        Recipe recipe = new Recipe();
        recipe.setId(100L);
        when(recipeService.findById( anyLong() )).thenReturn(recipe);

        // When
        // Then
        mockMvc.perform( get("/recipe/100/show") )
                .andExpect( status().isOk() )
                .andExpect( view().name("recipe/show"))
                .andExpect( model().attributeExists("recipe"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        // Given
        // When
        // Then
        mockMvc.perform( get("/recipe/new") )
                .andExpect( status().isOk() )
                .andExpect( view().name("recipe/recipeform") )
                .andExpect( model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        // Given
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(2L);

        when( recipeService.saveRecipeCommand( any() ) ).thenReturn(cmd);

        // When
        // Then
        mockMvc.perform(
                post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "")
                        .param("description", "some string"))
                .andExpect( status().is3xxRedirection())
                .andExpect( view().name("redirect:/recipe/2/show/"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        // Given
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(2L);

        when( recipeService.findCommandById( anyLong() )).thenReturn(cmd);

        // When
        // Then
        mockMvc.perform( get("/recipe/1/update") )
                .andExpect( status().isOk() )
                .andExpect( view().name("recipe/recipeform"))
                .andExpect( model().attributeExists("recipe") );
    }
}