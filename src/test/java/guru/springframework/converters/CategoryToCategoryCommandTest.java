package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {
    private static final Long ID = 100L;
    private static final String DESCRIPTION = "foo";

    CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNull() { assertNull(converter.convert(null));}
    @Test
    public void testEmptyCategory() { assertNotNull(converter.convert(new Category()));}
    @Test
    public void convert() {
        // Given
        Category cat = new Category();
        cat.setId(ID);
        cat.setDescription(DESCRIPTION);

        // When
        CategoryCommand actualCmd = converter.convert(cat);

        // Then
        assertNotNull(actualCmd);
        assertEquals(ID, actualCmd.getId());
        assertEquals(DESCRIPTION, actualCmd.getDescription());
    }
}