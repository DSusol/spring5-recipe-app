package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageServiceImpl serviceUnderTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serviceUnderTest = new ImageServiceImpl(recipeRepository);
    }

    @SneakyThrows
    @Test
    public void saveImage() {
        //given
        MultipartFile file = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "test".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        when(recipeRepository.findById(2L)).thenReturn(Optional.of(recipe));
        serviceUnderTest.saveImage(2L, file);

        //then
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(file.getBytes().length, savedRecipe.getImage().length);
    }
}