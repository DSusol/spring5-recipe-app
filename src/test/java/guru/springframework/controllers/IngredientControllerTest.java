package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controllerUnderTest;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controllerUnderTest = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void displayIngredientList() throws Exception {
        //given
        RecipeCommand recipe = new RecipeCommand();
        IngredientCommand ingredient1 = new IngredientCommand();
        IngredientCommand ingredient2 = new IngredientCommand();
        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipe);

        //then
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attribute("recipe", instanceOf(RecipeCommand.class)));

        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    public void displayIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findByRecipeIdAndIngredientId(2L, 4L)).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/2/ingredient/4/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attribute("ingredient", instanceOf(IngredientCommand.class)));

        verify(ingredientService).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void createOrUpdateIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        UnitOfMeasureCommand uom1 = new UnitOfMeasureCommand();
        uom1.setId(1L);
        UnitOfMeasureCommand uom2 = new UnitOfMeasureCommand();
        uom1.setId(2L);
        unitOfMeasureCommands.add(uom1);
        unitOfMeasureCommands.add(uom2);

        //when
        when(ingredientService.findByRecipeIdAndIngredientId(1L, 2L)).thenReturn(ingredientCommand);
        when(unitOfMeasureService.findUomCommands()).thenReturn(unitOfMeasureCommands);

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attribute("ingredient", instanceOf(IngredientCommand.class)))
                .andExpect(model().attribute("uomList", hasSize(2)));

        verify(ingredientService).findByRecipeIdAndIngredientId(anyLong(), anyLong());
        verify(unitOfMeasureService).findUomCommands();
    }

    @Test
    public void createOrUpdate() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setId(2L);

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(post("/recipe/2/ingredient"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/2/show"));
    }

    @Test
    public void newIngredient() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        UnitOfMeasureCommand upm1 = new UnitOfMeasureCommand();
        upm1.setId(1L);
        UnitOfMeasureCommand upm2 = new UnitOfMeasureCommand();
        upm2.setId(2L);
        unitOfMeasureCommands.add(upm1);
        unitOfMeasureCommands.add(upm2);

        //when
        when(unitOfMeasureService.findUomCommands()).thenReturn(unitOfMeasureCommands);
        when(recipeService.findCommandById(2L)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/2/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attribute("ingredient", instanceOf(IngredientCommand.class)))
                .andExpect(model().attribute("uomList", hasSize(2)));

        verify(unitOfMeasureService).findUomCommands();
    }
}