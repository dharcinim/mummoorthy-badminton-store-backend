package com.mummoorthy.badmintonstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mummoorthy.badmintonstore.entity.Brand;
import com.mummoorthy.badmintonstore.entity.Category;
import com.mummoorthy.badmintonstore.repository.BrandRepository;
import com.mummoorthy.badmintonstore.repository.CategoryRepository;
import com.mummoorthy.badmintonstore.entity.SubCategory;
import com.mummoorthy.badmintonstore.repository.SubCategoryRepository;

//@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public DatabaseSeeder(BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            SubCategoryRepository subCategoryRepository) {

	this.brandRepository = brandRepository;
	this.categoryRepository = categoryRepository;
	this.subCategoryRepository = subCategoryRepository;
	}

    @Override
    public void run(String... args) {

        seedBrands();
        seedCategories();
        seedSubCategories();
    }

    private void seedBrands() {

        if (brandRepository.count() > 0) {
            return;
        }

        Brand hundred = new Brand();
        hundred.setName("Hundred");
        hundred.setDescription("Hundred Badminton");
        hundred.setActive(true);

        brandRepository.save(hundred);
    }

    private void seedCategories() {

        if (categoryRepository.count() > 0) {
            return;
        }

        String[] categories = {
                "Rackets",
                "Shoes",
                "Shuttlecocks",
                "Strings",
                "Grips",
                "Clothing",
                "Accessories",
                "Fitness Equipment",
                "Kit Bags"
        };

        for (String categoryName : categories) {

            Category category = new Category();
            category.setName(categoryName);
            category.setActive(true);

            categoryRepository.save(category);
        }
    }

private void seedSubCategories() {

    if (subCategoryRepository.count() > 0) {
        return;
    }

    createSubCategory("All Rackets", "Rackets");
    createSubCategory("All Shoes", "Shoes");
    createSubCategory("All Shuttlecocks", "Shuttlecocks");
    createSubCategory("All Strings", "Strings");
    createSubCategory("All Grips", "Grips");
    createSubCategory("All Clothing", "Clothing");
    createSubCategory("All Accessories", "Accessories");
    createSubCategory("All Fitness Equipment", "Fitness Equipment");
    createSubCategory("All Kit Bags", "Kit Bags");
}
private void createSubCategory(String subCategoryName, String categoryName) {

    Category category = categoryRepository.findAll()
            .stream()
            .filter(c -> c.getName().equals(categoryName))
            .findFirst()
            .orElse(null);

    if (category == null) {
        return;
    }

    SubCategory subCategory = new SubCategory();
    subCategory.setName(subCategoryName);
    subCategory.setCategory(category);
    subCategory.setActive(true);

    subCategoryRepository.save(subCategory);
}


}