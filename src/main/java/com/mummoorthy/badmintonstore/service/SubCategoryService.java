package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.SubCategory;

public interface SubCategoryService {

    List<SubCategory> getAllSubCategories();

    SubCategory getSubCategoryById(Long id);

    SubCategory saveSubCategory(SubCategory subCategory);

    SubCategory updateSubCategory(Long id, SubCategory subCategory);

    void deleteSubCategory(Long id);
}