package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    CategoryToCategoryCommand converterInderTest;

    @Before
    public void setUp() throws Exception {
        converterInderTest = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject(){
        assertNull(converterInderTest.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converterInderTest.convert(new Category()));
    }

    @Test
    public void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converterInderTest.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }
}