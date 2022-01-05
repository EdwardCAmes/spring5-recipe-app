package guru.springframework.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // use the db's generated auto generated id upon persistence
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Lob // BLOB in db (Binary Large OBject field)
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)  // Overrides default EnumType.ORDINAL --> 1-N,  if added types would mess up previous entries
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL)  // Makes this the 'owner'
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")  // Makes this the 'owner'
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name="recipe_category", // stops making 2 tables 'recipe_categories' and 'category_recipes'.
            joinColumns = @JoinColumn(name="recipe_id"),  // define the column names in the table
            inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
        return this;
    }
}
