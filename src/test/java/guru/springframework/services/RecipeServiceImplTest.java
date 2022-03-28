package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl underTest;
    RecipeRepository recipeRepository = mock(RecipeRepository.class);

    @Before
    public void setUp() throws Exception {
        underTest = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void findRecipes() {

        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe1.setDescription("1st");
        recipe2.setDescription("2nd");
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe1);
        recipeData.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = underTest.findRecipes();

        assertEquals(2, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }
}