package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {

    private static final Long ID = 100L;
    private static final Long UOM_ID = ID + 1;
    private static final String DESCRIPTION = "foo";
    private static final BigDecimal AMOUNT = new BigDecimal(2);

    IngredientToIngredientCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyIngredient() {
        assertNotNull(converter.convert(new Ingredient()));
    }
    @Test
    public void convert() {
        // Given
        Ingredient i = new Ingredient();
        i.setDescription(DESCRIPTION);
        i.setAmount(AMOUNT);
        i.setId(ID);
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        i.setUom(uom);

        // When
        IngredientCommand actualCommand = converter.convert(i);

        // Then
        assertNotNull(actualCommand);
        assertNotNull(actualCommand.getUom());
        assertEquals(ID, actualCommand.getId());
        assertEquals(AMOUNT, actualCommand.getAmount());
        assertEquals(DESCRIPTION, actualCommand.getDescription());
        assertEquals(UOM_ID, actualCommand.getUom().getId());
    }
    @Test
    public void convertWithNullUom() {
        // Given
        Ingredient i = new Ingredient();
        i.setDescription(DESCRIPTION);
        i.setAmount(AMOUNT);
        i.setId(ID);

        // When
        IngredientCommand actualCommand = converter.convert(i);

        // Then
        assertNotNull(actualCommand);
        assertNull(actualCommand.getUom());
        assertEquals(ID, actualCommand.getId());
        assertEquals(AMOUNT, actualCommand.getAmount());
        assertEquals(DESCRIPTION, actualCommand.getDescription());
    }
}