package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Cup";

    UnitOfMeasureCommandToUnitOfMeasure converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testForNullObject() {
        assertNull(converterUnderTest.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converterUnderTest.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure unitOfMeasure  = converterUnderTest.convert(unitOfMeasureCommand);

        //then
        assertEquals(ID, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
    }
}