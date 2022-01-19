package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {
    private static final Long ID = 100L;
    private static final String DESCRIPTION = "foo";

    NotesCommandToNotes converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyNotesCommand() {
        assertNotNull(converter.convert(new NotesCommand()));
    }
    @Test
    public void testConvert() {
        // Given
        NotesCommand cmd = new NotesCommand();
        cmd.setId(ID);
        cmd.setRecipeNotes(DESCRIPTION);

        // When
        Notes actualNotes = converter.convert(cmd);

        // Then
        assertNotNull(actualNotes);
        assertEquals(ID, actualNotes.getId());
        assertEquals(DESCRIPTION, actualNotes.getRecipeNotes());
    }
}