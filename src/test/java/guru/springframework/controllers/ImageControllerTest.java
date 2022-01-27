package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    ImageController controller;
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getImageForm() throws Exception {
        // Given
        RecipeCommand rc = new RecipeCommand();
        rc.setId(1L);

        when( recipeService.findCommandById( anyLong() ) ).thenReturn(rc);

        // When
        mockMvc.perform( get("/recipe/1/image"))
                .andExpect( status().isOk() )
                .andExpect( model().attributeExists("recipe"));

        // THen
        verify( recipeService, times(1)).findCommandById( anyLong() );
    }

    @Test
    public void handleImagePost() throws Exception {
        // Given
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());
        // When
        mockMvc.perform( multipart("/recipe/1/image").file(multipartFile))
                .andExpect( status().is3xxRedirection() )
                .andExpect( header().string("Location", "/recipe/1/show"));

        // THen
        verify( imageService, times(1)).saveImageFile( anyLong(), any( ));
    }

    @Test
    public void renderImageFromDb() throws Exception {
        // Given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String s = "Fake image data";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];
        int i = 0;
        for (byte primativeByte : s.getBytes()) {
            bytesBoxed[i++] = primativeByte;
        }

        recipeCommand.setImage(bytesBoxed);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // THen
        byte [] responseBytes = response.getContentAsByteArray();
        assertEquals(bytesBoxed.length, responseBytes.length);
    }
}