package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final Long ID = 100L;
    public static final String DESCRIPTION = "foo";
    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }
    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyCommand() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }
    @Test
    public void testConvert() {
        // Given
        UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
        uomc.setId(ID);
        uomc.setDescription(DESCRIPTION);

        // When
        UnitOfMeasure actualUom = converter.convert(uomc);

        // Then
        assertNotNull(actualUom);
        assertEquals(ID, actualUom.getId());
        assertEquals(DESCRIPTION, actualUom.getDescription());
    }
}