package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    private static final Long ID = 100L;
    private static final String DESCRIPTION = "ffoo";

    NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyNotes() {
        assertNotNull(converter.convert(new Notes()));
    }
    @Test
    public void testConvert() {
        // Given
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(DESCRIPTION);

        // When
        NotesCommand actualCmd = converter.convert(notes);

        // Then
        assertNotNull(actualCmd);
        assertEquals(ID, actualCmd.getId());
        assertEquals(DESCRIPTION, actualCmd.getRecipeNotes());
    }
}