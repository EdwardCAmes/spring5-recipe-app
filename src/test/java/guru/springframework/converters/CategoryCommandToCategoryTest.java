package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    private static final Long ID = 100L;
    private static final String DESCRIPTION = "foo";

    CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNull() {  assertNull(converter.convert(null)); }
    @Test
    public void testEmptyCommand() { assertNotNull(converter.convert(new CategoryCommand())); }
    @Test
    public void convert() {
        // Given
        CategoryCommand cmd = new CategoryCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);

        // When
        Category actualCat = converter.convert(cmd);

        // Then
        assertNotNull(actualCat);
        assertEquals(ID, actualCat.getId());
        assertEquals(DESCRIPTION, actualCat.getDescription());
    }
}