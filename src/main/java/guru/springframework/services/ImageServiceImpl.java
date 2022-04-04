package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImage(Long recipeId, MultipartFile file) {

        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        assert recipe != null;

        try {
            Byte[] imageBytes = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                imageBytes[i++] = b;
            }

            recipe.setImage(imageBytes);

            recipeRepository.save(recipe);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
