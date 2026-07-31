package com.mummoorthy.badmintonstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mummoorthy.badmintonstore.repository.BrandRepository;
import com.mummoorthy.badmintonstore.repository.CategoryRepository;
import com.mummoorthy.badmintonstore.repository.SubCategoryRepository;
import com.mummoorthy.badmintonstore.repository.ProductRepository;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;
import com.mummoorthy.badmintonstore.entity.Brand;
import com.mummoorthy.badmintonstore.entity.Category;
import com.mummoorthy.badmintonstore.entity.Product;
import com.mummoorthy.badmintonstore.entity.SubCategory;

//@Component
public class ProductSeeder implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    public ProductSeeder(
            BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            SubCategoryRepository subCategoryRepository,
            ProductRepository productRepository,
            ProductVariantRepository productVariantRepository) {

        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public void run(String... args) {

        if (productRepository.count() > 0) {
            return;
        }

        seedProducts();
    }

    private void seedProducts() {

        Brand hundred = brandRepository.findByName("Hundred")
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        createProduct("Nitrix Attack", "nitrix-attack",
                hundred,
                categoryRepository.findByName("Rackets").orElseThrow(),
                getSubCategory(categoryRepository.findByName("Rackets").orElseThrow()));

        createProduct("Flareon Typhoon", "flareon-typhoon",
                hundred,
                categoryRepository.findByName("Rackets").orElseThrow(),
                getSubCategory(categoryRepository.findByName("Rackets").orElseThrow()));

        createProduct("Powertek 1000 JR", "powertek-1000-jr",
                hundred,
                categoryRepository.findByName("Rackets").orElseThrow(),
                getSubCategory(categoryRepository.findByName("Rackets").orElseThrow()));

        createProduct("Nitrix 70 Venom", "nitrix-70-venom",
                hundred,
                categoryRepository.findByName("Rackets").orElseThrow(),
                getSubCategory(categoryRepository.findByName("Rackets").orElseThrow()));
    }
    private void createRacket(
            String name,
            String slug,
            Brand brand,
            Category category,
            SubCategory subCategory) {

        Product product = new Product();

        product.setName(name);
        product.setSlug(slug);

        product.setDescription(name + " badminton racket.");

        product.setShortDescription(name);

        product.setBrand(brand);

        product.setCategory(category);

        product.setSubCategory(subCategory);

        product.setCountryOfOrigin("India");

        product.setWarranty("6 Months");

        product.setGstPercentage(18.0);

        product.setFeatured(true);

        product.setBestSeller(true);

        product.setNewArrival(true);

        product.setActive(true);

        productRepository.save(product);

    }
    private Product createProduct(
            String name,
            String slug,
            Brand brand,
            Category category,
            SubCategory subCategory) {

    	if (productRepository.findBySlug(slug).isPresent()) {
    	    return productRepository.findBySlug(slug).get();
    	}

        Product product = new Product();
       
        product.setName(name);
        product.setSlug(slug);

        product.setDescription(name);
        product.setShortDescription(name);

        product.setBrand(brand);
        product.setCategory(category);
        product.setSubCategory(subCategory);

        product.setCountryOfOrigin("India");
        product.setWarranty("No Warranty");
        product.setGstPercentage(18.0);

        product.setFeatured(false);
        product.setBestSeller(false);
        product.setNewArrival(false);

        product.setActive(true);

        return productRepository.save(product);
    }
    
    private SubCategory getSubCategory(Category category) {

        String subCategoryName = "All " + category.getName();

        return subCategoryRepository.findByName(subCategoryName)
                .orElseThrow(() ->
                        new RuntimeException("SubCategory not found : " + subCategoryName));
    }
}