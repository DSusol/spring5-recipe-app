package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    final IngredientToIngredientCommand ingredientConverter;

    IngredientServiceImpl serviceUnderTest;

    public IngredientServiceImplTest() {
        this.ingredientConverter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serviceUnderTest = new IngredientServiceImpl(recipeRepository, ingredientConverter);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        //given
        final Long recipeId = 2L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        final Long ingredientId1 = 3L;
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ingredientId1);

        final Long ingredientId2 = 4L;
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredientId2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        //when
        when(recipeRepository.findById(2L)).thenReturn(Optional.of(recipe));
        IngredientCommand ingredientCommand = serviceUnderTest.findByRecipeIdAndIngredientId(2L, 4L);

        //then
        assertNotNull(ingredientCommand);
        assertEquals(ingredientId2, ingredientCommand.getId());
        verify(recipeRepository).findById(anyLong());
    }
}