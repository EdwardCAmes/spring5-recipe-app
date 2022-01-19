package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null) {
            return null;
        }
        final Category cat = new Category();
        cat.setId(categoryCommand.getId());
        cat.setDescription(categoryCommand.getDescription());
        return cat;
    }
}
