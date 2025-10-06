package com.oldtownbarber.category_service.controller;

import com.oldtownbarber.category_service.model.Category;
import com.oldtownbarber.category_service.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "API para la gestión y consulta de categorías")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/salon/{id}")
    @Operation(summary = "Obtener categorías por salón", description = "Obtiene todas las categorías asociadas a un salón específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías obtenidas exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Set<Category>> getCategoriesBySalon(
            @PathVariable
            @Parameter(description = "ID del salón", required = true) Long id) {
        Set<Category> categories = categoryService.getAllCategoriesBySalon(id);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content)
    })
    public ResponseEntity<Category> getCategoryById(
            @PathVariable
            @Parameter(description = "ID de la categoría", required = true) Long id) throws Exception {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}