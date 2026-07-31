package com.mummoorthy.badmintonstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.entity.SubCategory;
import com.mummoorthy.badmintonstore.service.SubCategoryService;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = "*")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public List<SubCategory> getAllSubCategories() {
        return subCategoryService.getAllSubCategories();
    }

    @GetMapping("/{id}")
    public SubCategory getSubCategoryById(@PathVariable Long id) {
        return subCategoryService.getSubCategoryById(id);
    }

    @PostMapping
    public SubCategory saveSubCategory(@RequestBody SubCategory subCategory) {
        return subCategoryService.saveSubCategory(subCategory);
    }

    @PutMapping("/{id}")
    public SubCategory updateSubCategory(@PathVariable Long id,
                                         @RequestBody SubCategory subCategory) {
        return subCategoryService.updateSubCategory(id, subCategory);
    }

    @DeleteMapping("/{id}")
    public String deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
        return "SubCategory deleted successfully";
    }
}