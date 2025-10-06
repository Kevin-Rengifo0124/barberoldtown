package com.oldtownbarber.category_service.service.implementation;

import com.oldtownbarber.category_service.dto.SalonDTO;
import com.oldtownbarber.category_service.model.Category;
import com.oldtownbarber.category_service.repository.CategoryRepository;
import com.oldtownbarber.category_service.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setSalonId(salonDTO.getId());
        newCategory.setImage(category.getImage());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new Exception("La categoría con el id no existe" + id);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {

        Category category = getCategoryById(id);
        if (!category.getSalonId().equals(salonId)) {
            throw new Exception("El salón no existe con el ID" + salonId);
        }
        categoryRepository.deleteById(id);
    }
}
