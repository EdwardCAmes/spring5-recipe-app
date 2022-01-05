package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // use the db's generated auto generated id upon persistence
    private Long id;
    private String description;

    @ManyToMany(mappedBy = "categories")  // use only 1 table, says which column in the join table defines it
    private Set<Recipe> recipes;

}
