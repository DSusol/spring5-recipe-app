package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Recipe Description";
    public static final Integer PREP_TIME = 10;
    public static final Integer COOK_TIME = 20;
    public static final Integer SOURCE = 30;
    public static final Integer SERVINGS = 3;
    public static final String URL = "http://example.com/";
    public static final String DIRECTIONS = "Recipe Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Long NOTES_ID = 5L;

    RecipeCommandToRecipe converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes(),
                new CategoryCommandToCategory()
        );
    }
    @Test
    public void testForNullObject() {
        assertNull(converterUnderTest.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converterUnderTest.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);

        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(10L);
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(20L);
        recipeCommand.getIngredients().add(ingredient1);
        recipeCommand.getIngredients().add(ingredient2);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        recipeCommand.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        recipeCommand.getCategories().add(category);

        //when
        Recipe recipe = converterUnderTest.convert(recipeCommand);

        //then
        assertEquals(ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(1, recipe.getCategories().size());
    }
}