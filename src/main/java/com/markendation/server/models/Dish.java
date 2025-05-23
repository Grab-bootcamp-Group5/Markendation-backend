package com.markendation.server.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.markendation.server.dto.DishDto;
import com.markendation.server.dto.IngredientDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Dish {
    @Id
    private String id;

    private String vietnameseName;

    private String name;

    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();

    @Builder.Default
    private List<Ingredient> optionalIngredients = new ArrayList<>();

    private Integer servings;

    private String imageUrl;

    public void update(DishDto dto) {
        if (dto.getId() != null) id = dto.getId();
        vietnameseName = dto.getVietnameseName();
        name = dto.getName();
        servings = dto.getServings();
        imageUrl = dto.getImageUrl();
        
        ingredients.clear();
        for (IngredientDto ingredientDto : dto.getIngredients()) {
            Ingredient ingredient = new Ingredient();
            ingredient.update(ingredientDto);

            ingredients.add(ingredient);
        }

        optionalIngredients.clear();
        for (IngredientDto ingredientDto : dto.getOptionalIngredients()) {
            Ingredient ingredient = new Ingredient();
            ingredient.update(ingredientDto);

            optionalIngredients.add(ingredient);
        }
    }
}
