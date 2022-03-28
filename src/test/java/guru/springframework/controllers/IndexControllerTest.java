package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    RecipeService recipeService = mock(RecipeService.class);
    Model model = mock(Model.class);
    IndexController underTestController;

    @Before
    public void setUp() throws Exception {
        underTestController = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() {

        //given
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(Recipe.builder().id(1L).build());
        recipeSet.add(Recipe.builder().id(2L).build());

        when(recipeService.findRecipes()).thenReturn(recipeSet);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String templateName = underTestController.getIndexPage(model);

        //then
        assertEquals("index", templateName);
        verify(recipeService, times(1)).findRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> verifiedSet = argumentCaptor.getValue();
        assertEquals(2, verifiedSet.size());
    }
}