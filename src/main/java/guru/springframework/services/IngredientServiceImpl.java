package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;

        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        if (recipe == null) {
            throw new RuntimeException("Recipe cannot be found by id.");
        }

        Ingredient foundIngredient = null;
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.getId().equals(ingredientId)) {
                foundIngredient = ingredient;
                break;
            }
        }

        if (foundIngredient == null) {
            throw new RuntimeException("Ingredient cannot be found by id.");
        }

        return ingredientToIngredientCommand.convert(foundIngredient);
    }
}
