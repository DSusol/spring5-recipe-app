package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Apple";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID = 2L;

    IngredientToIngredientCommand converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testForNullObject() {
        assertNull(converterUnderTest.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converterUnderTest.convert(new Ingredient()));
    }

    @Test
    public void convert() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        ingredient.setUom(unitOfMeasure);

        //when
        IngredientCommand ingredientCommand = converterUnderTest.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNotNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(ID, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
    }

    @Test
    public void convert_withNullUnitOfMeasure() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setUom(null);

        //when
        IngredientCommand ingredientCommand = converterUnderTest.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNull(ingredientCommand.getUnitOfMeasure());
    }
}