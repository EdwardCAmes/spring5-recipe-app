package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null){
            return null;
        }
        final UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setId(unitOfMeasure.getId());
        uom.setDescription(unitOfMeasure.getDescription());
        return uom;
    }
}
