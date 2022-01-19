package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {
    private static final Long ID = 100L;
    private static final Long UOM_ID = ID + 1;
    private static final String DESCRIPTION = "foo";
    private static final BigDecimal AMOUNT = new BigDecimal(20.3);

    IngredientCommandToIngredient converter;
    @Before
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNull() {
        assertNull(converter.convert(null));
    }
    @Test
    public void testEmptyCommand() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }
    @Test
    public void testConvert() {
        // Given
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);
        cmd.setAmount(AMOUNT);
        UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
        uomc.setId(UOM_ID);
        cmd.setUom(uomc);

        // When
        Ingredient actualIngredient = converter.convert(cmd);

        // Then
        assertNotNull(actualIngredient);
        assertNotNull(actualIngredient.getUom());
        assertEquals(ID, actualIngredient.getId());
        assertEquals(DESCRIPTION, actualIngredient.getDescription());
        assertEquals(AMOUNT, actualIngredient.getAmount());
        assertEquals(UOM_ID, actualIngredient.getUom().getId());
    }
    @Test
    public void testConvertNullUom() {
        // Given
        IngredientCommand cmd = new IngredientCommand();
        cmd.setId(ID);
        cmd.setDescription(DESCRIPTION);
        cmd.setAmount(AMOUNT);

        // When
        Ingredient actualIngredient = converter.convert(cmd);

        // Then
        assertNotNull(actualIngredient);
        assertNull(actualIngredient.getUom());
        assertEquals(ID, actualIngredient.getId());
        assertEquals(DESCRIPTION, actualIngredient.getDescription());
        assertEquals(AMOUNT, actualIngredient.getAmount());
    }
}