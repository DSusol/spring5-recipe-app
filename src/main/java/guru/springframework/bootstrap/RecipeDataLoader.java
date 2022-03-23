package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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

        Ingredient avocadosForGuacamole = new Ingredient();
        avocadosForGuacamole.setDescription("Avocados");
        avocadosForGuacamole.setAmount(BigDecimal.valueOf(2.0));
        avocadosForGuacamole.setUom(quantity);
        avocadosForGuacamole.setRecipe(perfectGuacamole);
        perfectGuacamole.getIngredients().add(avocadosForGuacamole);

        Ingredient saltForGuacamole = new Ingredient();
        saltForGuacamole.setDescription("Salt");
        saltForGuacamole.setAmount(BigDecimal.valueOf(0.25));
        saltForGuacamole.setUom(teaspoon);
        saltForGuacamole.setRecipe(perfectGuacamole);
        perfectGuacamole.getIngredients().add(saltForGuacamole);

        Ingredient fleshLimeForGuacamole = new Ingredient();
        fleshLimeForGuacamole.setDescription("Flesh Lime");
        fleshLimeForGuacamole.setAmount(BigDecimal.valueOf(1));
        fleshLimeForGuacamole.setUom(tableSpoon);
        fleshLimeForGuacamole.setRecipe(perfectGuacamole);
        perfectGuacamole.getIngredients().add(fleshLimeForGuacamole);

        Ingredient redOnionForGuacamole = new Ingredient();
        redOnionForGuacamole.setDescription("Red Onion");
        redOnionForGuacamole.setAmount(BigDecimal.valueOf(3));
        redOnionForGuacamole.setUom(tableSpoon);
        redOnionForGuacamole.setRecipe(perfectGuacamole);
        perfectGuacamole.getIngredients().add(redOnionForGuacamole);

        Ingredient groundBlackPaperForGuacamole = new Ingredient();
        groundBlackPaperForGuacamole.setDescription("Ground Black Paper");
        groundBlackPaperForGuacamole.setAmount(BigDecimal.valueOf(1));
        groundBlackPaperForGuacamole.setUom(pinch);
        groundBlackPaperForGuacamole.setRecipe(perfectGuacamole);
        perfectGuacamole.getIngredients().add(groundBlackPaperForGuacamole);

        perfectGuacamole.setDifficulty(easyDifficulty);

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes("Perfect Guacamole Notes...");
        perfectGuacamoleNotes.setRecipe(perfectGuacamole);
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

        Ingredient anchoChiliPowderForGrilledChicken = new Ingredient();
        anchoChiliPowderForGrilledChicken.setDescription("Ancho Chili Powder");
        anchoChiliPowderForGrilledChicken.setAmount(BigDecimal.valueOf(2.0));
        anchoChiliPowderForGrilledChicken.setUom(tableSpoon);
        anchoChiliPowderForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(anchoChiliPowderForGrilledChicken);

        Ingredient driedOreganoForGrilledChicken = new Ingredient();
        driedOreganoForGrilledChicken.setDescription("Dried Oregano");
        driedOreganoForGrilledChicken.setAmount(BigDecimal.valueOf(1));
        driedOreganoForGrilledChicken.setUom(teaspoon);
        driedOreganoForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(driedOreganoForGrilledChicken);

        Ingredient driedCuminForGrilledChicken = new Ingredient();
        driedCuminForGrilledChicken.setDescription("Dried Cumin");
        driedCuminForGrilledChicken.setAmount(BigDecimal.valueOf(1));
        driedCuminForGrilledChicken.setUom(teaspoon);
        driedCuminForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(driedCuminForGrilledChicken);

        Ingredient sugarForGrilledChicken = new Ingredient();
        sugarForGrilledChicken.setDescription("Sugar");
        sugarForGrilledChicken.setAmount(BigDecimal.valueOf(1));
        sugarForGrilledChicken.setUom(teaspoon);
        sugarForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(sugarForGrilledChicken);

        Ingredient saltForGrilledChicken = new Ingredient();
        saltForGrilledChicken.setDescription("Salt");
        saltForGrilledChicken.setAmount(BigDecimal.valueOf(0.5));
        saltForGrilledChicken.setUom(teaspoon);
        saltForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(saltForGrilledChicken);

        Ingredient garlicForGrilledChicken = new Ingredient();
        garlicForGrilledChicken.setDescription("Garlic");
        garlicForGrilledChicken.setAmount(BigDecimal.valueOf(1));
        garlicForGrilledChicken.setUom(quantity);
        garlicForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(garlicForGrilledChicken);

        Ingredient orangeJuiceForGrilledChicken = new Ingredient();
        orangeJuiceForGrilledChicken.setDescription("Orange Juice");
        orangeJuiceForGrilledChicken.setAmount(BigDecimal.valueOf(3));
        orangeJuiceForGrilledChicken.setUom(tableSpoon);
        orangeJuiceForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(orangeJuiceForGrilledChicken);

        Ingredient oliveOilJuiceForGrilledChicken = new Ingredient();
        oliveOilJuiceForGrilledChicken.setDescription("Olive Oil");
        oliveOilJuiceForGrilledChicken.setAmount(BigDecimal.valueOf(2));
        oliveOilJuiceForGrilledChicken.setUom(tableSpoon);
        oliveOilJuiceForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(oliveOilJuiceForGrilledChicken);

        Ingredient chickenForGrilledChicken = new Ingredient();
        chickenForGrilledChicken.setDescription("Boneless Chicken");
        chickenForGrilledChicken.setAmount(BigDecimal.valueOf(1.25));
        chickenForGrilledChicken.setUom(pounds);
        chickenForGrilledChicken.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(chickenForGrilledChicken);

        spicyGrilledChickenTacos.setDifficulty(moderateDifficulty);

        Notes spicyGrilledChickenTacosNotes = new Notes();
        spicyGrilledChickenTacosNotes.setRecipeNotes("Spicy Grilled Chicken Tacos Notes...");
        spicyGrilledChickenTacosNotes.setRecipe(spicyGrilledChickenTacos);
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
