package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    IndexController underTest;
    RecipeService recipeService = mock(RecipeService.class);
    Model model = mock(Model.class);

    @Before
    public void setUp() throws Exception {
        underTest = new IndexController(recipeService);
    }

    @Test
    public void getIndexPage() {
        assertEquals("index", underTest.getIndexPage(model));
        verify(recipeService, times(1)).findRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}