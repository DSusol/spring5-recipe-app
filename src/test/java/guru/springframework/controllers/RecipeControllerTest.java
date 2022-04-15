package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class RecipeControllerTest {

    RecipeService recipeService = mock(RecipeService.class);

    @InjectMocks
    RecipeController recipeControllerUnderTest;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeControllerUnderTest).build();
    }

    @Test
    public void showById() throws Exception {
        Recipe recipe = Recipe.builder().id(1L).build();
        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", instanceOf(Recipe.class)))
                .andExpect(view().name("recipe/show"));

        verify(recipeService).findById(anyLong());
    }

    @Test
    public void newRecipe() throws Exception {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", instanceOf(RecipeCommand.class)))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void updateRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(5L);
        when(recipeService.findCommandById(5L)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/5/update"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", instanceOf(RecipeCommand.class)))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void saveOrUpdate() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(3L);
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/3/show"));
    }

    @Test
    public void delete() throws Exception {

        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService).deleteById(anyLong());
    }

    @Test
    public void shouldThrowNotFoundException() throws Exception {

        Optional<Recipe> recipe = Optional.empty();

        when(recipeService.findById(2L)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/2/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
                .andExpect(model().attribute("exception", instanceOf(Exception.class)));
    }
}