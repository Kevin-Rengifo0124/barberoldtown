package com.oldtownbarber.category_service.controller;

import com.oldtownbarber.category_service.dto.SalonDTO;
import com.oldtownbarber.category_service.model.Category;
import com.oldtownbarber.category_service.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor
@Tag(name = "Salon Categories Management", description = "API para la gestión de categorías por propietarios de salones")
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    @Operation(summary = "Crear una nueva categoría", description = "Crea una nueva categoría para el salón del propietario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado",
                    content = @Content)
    })
    public ResponseEntity<Category> createCategory(
            @RequestBody @Valid
            @Parameter(description = "Datos de la categoría a crear", required = true) Category category) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        Category savedCategory = categoryService.saveCategory(category, salonDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría específica del salón del propietario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría eliminada exitosamente",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "No tienes permisos para eliminar esta categoría",
                    content = @Content)
    })
    public ResponseEntity<String> deleteCategory(
            @PathVariable
            @Parameter(description = "ID de la categoría a eliminar", required = true) Long id) throws Exception {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        categoryService.deleteCategoryById(id, salonDTO.getId());
        return ResponseEntity.ok("Categoria eliminada exitosamente!");
    }
}