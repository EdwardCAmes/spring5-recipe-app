package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class BootStrapData implements ApplicationListener<ContextRefreshedEvent> {
//    public class BootStrapData implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public BootStrapData(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        Optional<UnitOfMeasure> eachOptional = unitOfMeasureRepository.findByDescription("Each");
        Optional<UnitOfMeasure> tableSpoonOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> teaSpoonOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> dashOptional = unitOfMeasureRepository.findByDescription("Dash");
        Optional<UnitOfMeasure> cupsOptional = unitOfMeasureRepository.findByDescription("Cup");
        UnitOfMeasure eachUom = eachOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonOptional.get();
        UnitOfMeasure dashUom = dashOptional.get();
        UnitOfMeasure cupsom = cupsOptional.get();

        Optional<Category> mexicanOptional = categoryRepository.findByDescription("Mexican");
        Optional<Category> americanOptional = categoryRepository.findByDescription("American");
        Category mexicanCategory = mexicanOptional.get();
        Category americanCategory = americanOptional.get();

        //  Spicy Grilled Chicken
        Recipe spicyGrilledChicken = new Recipe();
        spicyGrilledChicken.setDescription("Spicy Grilled Chicken");
        spicyGrilledChicken.setCookTime(15);
        spicyGrilledChicken.setPrepTime(20);
        spicyGrilledChicken.setServings(4);
        spicyGrilledChicken.setDifficulty(Difficulty.MODERATE);
        spicyGrilledChicken.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyGrilledChicken.setDirections("Prepare a gas or charcoal grill for medium-high, direct heat\n" +
                "Make the marinade and coat the chicken:\n" +
                "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "Grill the chicken:\n" +
                "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "Warm the tortillas:\n" +
                "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "Assemble the tacos:\n" +
                "Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n");

        Notes chickenNotes = new Notes();
        chickenNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, " +
                "on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, " +
                "and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        spicyGrilledChicken.setNotes(chickenNotes);

        spicyGrilledChicken.addIngredient(new Ingredient("oregano", new BigDecimal(1.0), teaSpoonUom));
        spicyGrilledChicken.addIngredient(new Ingredient("cumin", new BigDecimal(1.0), teaSpoonUom));
        spicyGrilledChicken.addIngredient(new Ingredient("sugar", new BigDecimal(1.0), teaSpoonUom));
        spicyGrilledChicken.addIngredient(new Ingredient("salt", new BigDecimal(0.5), teaSpoonUom));
        spicyGrilledChicken.addIngredient(new Ingredient("grated orange zest", new BigDecimal(1), tableSpoonUom));
        spicyGrilledChicken.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tableSpoonUom));
        spicyGrilledChicken.addIngredient(new Ingredient("garlic", new BigDecimal(2), eachUom));
        spicyGrilledChicken.addIngredient(new Ingredient("sour cream", new BigDecimal(0.5), cupsom));

        spicyGrilledChicken.getCategories().add(mexicanCategory);
        spicyGrilledChicken.getCategories().add(americanCategory);

        recipes.add(spicyGrilledChicken);

        // Guacamole
        Recipe guacamole = new Recipe();
        guacamole.setDescription("Best Guacamole");
        guacamole.setCookTime(10);
        guacamole.setPrepTime(10);
        guacamole.setServings(2);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "How to make guacamole - scoring avocado\n" +
                "Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "How to make guacamole - smashing avocado with fork\n" +
                "Add remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.\n" +
                "\n" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        guacamole.setNotes(guacamoleNotes);

        guacamole.addIngredient(new Ingredient("avocados", new BigDecimal(1), eachUom));
        guacamole.addIngredient(new Ingredient("salt", new BigDecimal(0.25), teaSpoonUom));
        guacamole.addIngredient(new Ingredient("black pepper", new BigDecimal(1), dashUom));

        recipes.add(guacamole);

        return recipes;
    }
}
