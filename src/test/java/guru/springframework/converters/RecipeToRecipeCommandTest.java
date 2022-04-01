package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

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

    RecipeToRecipeCommand converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new RecipeToRecipeCommand(
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand(),
                new CategoryToCategoryCommand()
        );
    }


    @Test
    public void testForNullObject() {
        assertNull(converterUnderTest.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converterUnderTest.convert(new Recipe()));
    }

    @Test
    public void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(10L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(20L);
        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

        Category category = new Category();
        recipe.getCategories().add(category);

        //when
        RecipeCommand recipeCommand = converterUnderTest.convert(recipe);

        //then
        assertEquals(ID, recipeCommand.getId());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(2, recipeCommand.getIngredients().size());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(1, recipeCommand.getCategories().size());
    }
}