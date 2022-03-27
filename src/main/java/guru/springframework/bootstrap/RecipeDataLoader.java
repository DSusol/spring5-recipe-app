package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class RecipeDataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeDataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.debug("I'm in data loader.");
        Difficulty easyDifficulty = Difficulty.EASY;
        Difficulty moderateDifficulty = Difficulty.MODERATE;

        UnitOfMeasure quantity = getUnitOfMeasure("Quantity");
        UnitOfMeasure teaspoon = getUnitOfMeasure("Teaspoon");
        UnitOfMeasure tableSpoon = getUnitOfMeasure("Tablespoon");
        UnitOfMeasure pinch = getUnitOfMeasure("Pinch");
        UnitOfMeasure pounds = getUnitOfMeasure("Pounds");
        Category mexicanCategory = getCategory("Mexican");
        Category americanCategory = getCategory("American");

        // RECIPE = Perfect Guacamole
        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setSource("https://www.simplyrecipes.com");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setDirections("1. Cut the avocado.\n2. Mash the avocado flesh.\n" +
                "3. Add remaining ingredients to taste.\n4. Serve immediately.");
        perfectGuacamole.getCategories().add(mexicanCategory);

        Ingredient avocadosForGuacamole = new Ingredient("Avocados", new BigDecimal("2.0"), quantity);
        Ingredient saltForGuacamole = new Ingredient("Salt", new BigDecimal("0.25"), teaspoon);
        Ingredient fleshLimeForGuacamole = new Ingredient("Flesh Lime", new BigDecimal("1"), tableSpoon);
        Ingredient redOnionForGuacamole = new Ingredient("Red Onion", new BigDecimal("3"), tableSpoon);
        Ingredient groundBlackPaperForGuacamole = new Ingredient("Ground Black Paper", new BigDecimal("1"), pinch);

        perfectGuacamole.addIngredient(avocadosForGuacamole);
        perfectGuacamole.addIngredient(saltForGuacamole);
        perfectGuacamole.addIngredient(fleshLimeForGuacamole);
        perfectGuacamole.addIngredient(redOnionForGuacamole);
        perfectGuacamole.addIngredient(groundBlackPaperForGuacamole);

        perfectGuacamole.setDifficulty(easyDifficulty);

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes("Perfect Guacamole Notes...");
        perfectGuacamole.setNotes(perfectGuacamoleNotes);

        recipeRepository.save(perfectGuacamole);

        // RECIPE = Spicy Grilled Chicken Tacos
        Recipe spicyGrilledChickenTacos = new Recipe();
        spicyGrilledChickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        spicyGrilledChickenTacos.setPrepTime(20);
        spicyGrilledChickenTacos.setCookTime(15);
        spicyGrilledChickenTacos.setServings(5);
        spicyGrilledChickenTacos.setSource("https://www.simplyrecipes.com");
        spicyGrilledChickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyGrilledChickenTacos.setDirections("1. Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2. Make the marinade and coat the chicken.\n" +
                "3. Grill the chicken.\n" +
                "4. Warm the tortillas.\n" +
                "5. Assemble the tacos.");
        spicyGrilledChickenTacos.getCategories().add(americanCategory);

        Ingredient anchoChiliPowderForGrilledChicken = new Ingredient("Ancho Chili Powder", new BigDecimal("2"), tableSpoon);
        Ingredient driedOreganoForGrilledChicken = new Ingredient("Dried Oregano", new BigDecimal("1"), teaspoon);
        Ingredient driedCuminForGrilledChicken = new Ingredient("Dried Cumin", new BigDecimal("1"), teaspoon);
        Ingredient sugarForGrilledChicken = new Ingredient("Sugar", new BigDecimal("1"), teaspoon);
        Ingredient saltForGrilledChicken = new Ingredient("Salt", new BigDecimal("0.5"), teaspoon);
        Ingredient garlicForGrilledChicken = new Ingredient("Garlic", new BigDecimal("1"), quantity);
        Ingredient orangeJuiceForGrilledChicken = new Ingredient("Orange Juice", new BigDecimal("3"), tableSpoon);
        Ingredient oliveOilJuiceForGrilledChicken = new Ingredient("Olive Oil", new BigDecimal("2"), tableSpoon);
        Ingredient chickenForGrilledChicken = new Ingredient("Boneless Chicken", new BigDecimal("1.25"), pounds);

        spicyGrilledChickenTacos.addIngredient(anchoChiliPowderForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(driedOreganoForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(driedCuminForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(sugarForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(saltForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(garlicForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(orangeJuiceForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(oliveOilJuiceForGrilledChicken);
        spicyGrilledChickenTacos.addIngredient(chickenForGrilledChicken);

        spicyGrilledChickenTacos.setDifficulty(moderateDifficulty);

        Notes spicyGrilledChickenTacosNotes = new Notes();
        spicyGrilledChickenTacosNotes.setRecipeNotes("Spicy Grilled Chicken Tacos Notes...");
        spicyGrilledChickenTacos.setNotes(spicyGrilledChickenTacosNotes);

        recipeRepository.save(spicyGrilledChickenTacos);
    }

    private UnitOfMeasure getUnitOfMeasure(String description) {
        return unitOfMeasureRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Unit of measure '" + description + "' not found"));
    }

    private Category getCategory(String description) {
        return categoryRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Category '" + description + "' not found"));
    }
}
