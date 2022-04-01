package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Test notes";

    NotesToNotesCommand converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new NotesToNotesCommand();
    }

    @Test
    public void testForNullObject() {
        assertNull(converterUnderTest.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converterUnderTest.convert(new Notes()));
    }

    @Test
    public void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(DESCRIPTION);

        //when
        NotesCommand notesCommand = converterUnderTest.convert(notes);

        //then
        assertEquals(ID, notesCommand.getId());
        assertEquals(DESCRIPTION, notesCommand.getRecipeNotes());
    }
}