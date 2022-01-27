package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{idStr}/image")
    public String getRecipe(@PathVariable String idStr, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(idStr)));
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{idStr}/image")
    public String handleImagePost(@PathVariable String idStr, @RequestParam("imagefile")MultipartFile file) {
        imageService.saveImageFile(Long.parseLong(idStr), file);
        return "redirect:/recipe/" + idStr + "/show";
    }

    @GetMapping("/recipe/{idStr}/recipeimage")
    public void renderImageFromDb(@PathVariable String idStr, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.parseLong(idStr));
        byte[] byteArray = new byte[recipeCommand.getImage().length];
        int i = 0;
        for (Byte wrappedByte : recipeCommand.getImage()) {
            byteArray[i++] = wrappedByte; // auto unboxing
        }

        response.setContentType("imagee/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }
}
