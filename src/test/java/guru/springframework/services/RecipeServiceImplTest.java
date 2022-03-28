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

        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(Recipe.builder().id(1L).build());
        recipeSet.add(Recipe.builder().id(2L).build());

        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> recipes = underTest.findRecipes();

        assertEquals(2, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }
}