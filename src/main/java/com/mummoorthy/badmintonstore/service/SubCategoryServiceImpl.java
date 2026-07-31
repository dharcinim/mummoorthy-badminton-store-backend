package com.mummoorthy.badmintonstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.SubCategory;
import com.mummoorthy.badmintonstore.repository.SubCategoryRepository;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Override
    public SubCategory getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public SubCategory updateSubCategory(Long id, SubCategory subCategory) {

        SubCategory existing = subCategoryRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setName(subCategory.getName());
        existing.setDescription(subCategory.getDescription());
        existing.setImageUrl(subCategory.getImageUrl());
        existing.setActive(subCategory.getActive());
        existing.setCategory(subCategory.getCategory());

        return subCategoryRepository.save(existing);
    }

    @Override
    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
    }
}