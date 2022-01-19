package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final Long ID = 100L;
    public static final String DESCRIPTION = "Foo";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }
    @Test
    public void convert() {
        // Given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID);
        uom.setDescription(DESCRIPTION);

        // When
        UnitOfMeasureCommand actualCommand = converter.convert(uom);

        // Then
        assertNotNull(actualCommand);
        assertEquals(ID, actualCommand.getId());
        assertEquals(DESCRIPTION, actualCommand.getDescription());
    }
}