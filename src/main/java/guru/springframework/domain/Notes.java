package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = "recipe")
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // use the db's generated auto generated id upon persistence
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob  // Store more than 256 char in db field  --> CLOB type (LOB = Large OBject field)
    private String recipeNotes;

}
