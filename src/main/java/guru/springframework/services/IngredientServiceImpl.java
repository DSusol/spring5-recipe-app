package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        assert ingredient != null;

        Recipe relatedRecipe = recipeRepository.findById(ingredientCommand.getRecipeId()).orElse(null);


        if (relatedRecipe == null) {
            throw new RuntimeException("Ingredient doesn't have recipe.");
        }

        if (ingredientCommand.getId() == null) {
            ingredient.setId((long) (relatedRecipe.getIngredients().size() + 1));
            relatedRecipe.getIngredients().add(ingredient);
            return ingredientToIngredientCommand.convert(ingredient);
        }

        ingredient.setRecipe(relatedRecipe);
        for (Ingredient recipeIngredient : relatedRecipe.getIngredients()) {
            if (recipeIngredient.getId().equals(ingredientCommand.getId())) {
                recipeIngredient.setDescription(ingredient.getDescription());
                recipeIngredient.setAmount(ingredient.getAmount());
                recipeIngredient.setUom(ingredient.getUom());
                recipeRepository.save(relatedRecipe);
                return ingredientToIngredientCommand.convert(recipeIngredient);
            }
        }

        throw new RuntimeException("Incorrect recipe id found.");
    }
}
