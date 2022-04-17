package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
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
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageControllerUnderTest;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageControllerUnderTest = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageControllerUnderTest)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .build();
    }

    @Test
    public void changeImage() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        //when
        when(recipeService.findCommandById(2L)).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/2/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attribute("recipe", instanceOf(RecipeCommand.class)));

        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    public void saveImage() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/2/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));

        verify(imageService).saveImage(anyLong(), any(MultipartFile.class));
    }

    @Test
    public void renderImageFromDB() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        byte[] image = "fake image".getBytes();
        Byte[] boxedImage = new Byte[image.length];
        int i = 0;
        for (byte aByte : image) {
            boxedImage[i++] = aByte;
        }
        recipeCommand.setImage(boxedImage);

        //when
        when(recipeService.findCommandById(2L)).thenReturn(recipeCommand);

        //then
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/2/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseByte = response.getContentAsByteArray();
        assertEquals(image.length, responseByte.length);
    }

    @Test
    public void badRequestTest() throws Exception {
        mockMvc.perform(get("/recipe/test/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"))
                .andExpect(model().attribute("exception", instanceOf(Exception.class)));
    }
}