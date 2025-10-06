package com.oldtownbarber.category_service.service;

import com.oldtownbarber.category_service.dto.SalonDTO;
import com.oldtownbarber.category_service.model.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);

    Set<Category> getAllCategoriesBySalon(Long id);

    Category getCategoryById(Long id) throws Exception;

    void deleteCategoryById(Long id, Long salonId) throws Exception;



}
