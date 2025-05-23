package com.markendation.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.markendation.server.classes.PageDishResponse;
import com.markendation.server.classes.PageIngredientResponse;
import com.markendation.server.dto.DishDto;
import com.markendation.server.dto.IngredientDto;
import com.markendation.server.services.DishService;
import com.markendation.server.services.IngredientService;
import com.markendation.server.services.S3Service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/public")
public class PublicController {
    private final IngredientService ingredientService;
    private final DishService dishService;
    private final S3Service s3Service;

    public PublicController(IngredientService ingredientService, DishService dishService, S3Service s3Service) {
        this.s3Service = s3Service;
        this.ingredientService = ingredientService;
        this.dishService = dishService;
    }

    @GetMapping("/ingredients/totalSize")
    public ResponseEntity<Integer> getTotalIngredients() {
        Integer totalSize = ingredientService.getTotal();
        return new ResponseEntity<>(totalSize, HttpStatus.OK);
    }

    @GetMapping("/dishes/totalSize")
    public ResponseEntity<Integer> getTotalDishes() {
        Integer totalSize = dishService.getTotal();
        return new ResponseEntity<>(totalSize, HttpStatus.OK);
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDto> getIngredient(@PathVariable("id") String ingredientId) throws IOException {
        IngredientDto response = ingredientService.getIngredient(ingredientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/ingredients")
    public ResponseEntity<PageIngredientResponse> getPageIngredients(@RequestParam(defaultValue = "0") Integer pageNo, 
                                        @RequestParam(defaultValue = "30") Integer pageSize, @RequestParam(defaultValue = "") String pattern) {
        PageIngredientResponse response = ingredientService.getPageIngredients(pageNo, pageSize, pattern);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dishes/{id}")
    public ResponseEntity<DishDto> getDish(@PathVariable("id") String dishId) throws IOException {
        DishDto response = dishService.getDish(dishId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dishes")
    public ResponseEntity<PageDishResponse> getPageDishes(@RequestParam(defaultValue = "0") Integer pageNo, 
                                        @RequestParam(defaultValue = "30") Integer pageSize, @RequestParam(defaultValue = "") String pattern) {
        PageDishResponse response = dishService.getPageDishes(pageNo, pageSize, pattern);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            s3Service.uploadImage(file);
            return ResponseEntity.ok("Upload successful");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}
