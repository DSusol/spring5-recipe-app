package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Cup";

    UnitOfMeasureToUnitOfMeasureCommand converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testForNullObject() {
        assertNull(converterUnderTest.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converterUnderTest.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureCommand unitOfMeasureCommand = converterUnderTest.convert(unitOfMeasure);

        //then
        assertEquals(ID, unitOfMeasureCommand.getId());
        assertEquals(DESCRIPTION, unitOfMeasureCommand.getDescription());
    }
}