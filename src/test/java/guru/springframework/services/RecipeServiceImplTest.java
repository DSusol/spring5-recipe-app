package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl serviceUnderTest;
    RecipeRepository recipeRepository = mock(RecipeRepository.class);
    RecipeToRecipeCommand recipeToRecipeCommand = mock(RecipeToRecipeCommand.class);
    RecipeCommandToRecipe recipeCommandToRecipe = mock(RecipeCommandToRecipe.class);

    @Test
    public void findRecipes() {

        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(Recipe.builder().id(1L).build());
        recipeSet.add(Recipe.builder().id(2L).build());

        when(recipeRepository.findAll()).thenReturn(recipeSet);

        Set<Recipe> recipes = serviceUnderTest.findRecipes();

        assertEquals(2, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
        Long recipeId = 2L;
        Recipe recipe = Recipe.builder().id(recipeId).build();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        Recipe foundRecipe = serviceUnderTest.findById(recipeId);

        assertNotNull(foundRecipe);
        assertEquals(recipe, foundRecipe);
        assertEquals(recipeId, foundRecipe.getId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    public void findById_nullResult() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Recipe foundRecipe = serviceUnderTest.findById(1L);

        assertNull(foundRecipe);
        verify(recipeRepository).findById(anyLong());
    }
}