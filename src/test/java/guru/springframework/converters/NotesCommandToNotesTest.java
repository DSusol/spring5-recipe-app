package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "Test notes";

    NotesCommandToNotes converterUnderTest;

    @Before
    public void setUp() throws Exception {
        converterUnderTest = new NotesCommandToNotes();
    }

    @Test
    public void testForNullObject() {
        assertNull(converterUnderTest.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converterUnderTest.convert(new NotesCommand()));
    }

    @Test
    public void convert() {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(DESCRIPTION);

        //when
        Notes notes = converterUnderTest.convert(notesCommand);

        //then
        assertEquals(ID, notes.getId());
        assertEquals(DESCRIPTION, notes.getRecipeNotes());
    }
}