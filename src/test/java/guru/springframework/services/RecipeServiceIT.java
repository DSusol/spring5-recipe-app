package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String DESCRIPTION = "Recipe Description";
    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        //when
        recipeCommand.setDescription(DESCRIPTION);
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals(DESCRIPTION, savedRecipe.getDescription());
        assertEquals(recipe.getId(), savedRecipe.getId());
        assertEquals(recipe.getCategories().size(), savedRecipe.getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedRecipe.getIngredients().size());
    }
}
