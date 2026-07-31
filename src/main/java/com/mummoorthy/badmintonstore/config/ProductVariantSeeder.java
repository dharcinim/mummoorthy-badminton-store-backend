package com.mummoorthy.badmintonstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.mummoorthy.badmintonstore.entity.Product;
import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.repository.ProductRepository;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;

//@Component
public class ProductVariantSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    public ProductVariantSeeder(ProductRepository productRepository,
                                ProductVariantRepository productVariantRepository) {

        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
    }

    @Override
    public void run(String... args) {

    	if (productVariantRepository.count() > 0) {
            return;
        }

        seedVariants();
    }

    private void createVariant(
            Product product,
            String sku,
            Double mrp,
            Double sellingPrice,
            Integer stock,
            String weight,
            String gripSize) {

        ProductVariant variant = new ProductVariant();

        variant.setProduct(product);

        variant.setSku(sku);

        variant.setMrp(mrp);

        variant.setSellingPrice(sellingPrice);

        variant.setStock(stock);

        variant.setWeight(weight);

        variant.setGripSize(gripSize);

        variant.setBalance("Head Heavy");

        variant.setFlexibility("Medium");

        variant.setMaxTension("30 lbs");

        variant.setFrameMaterial("Carbon Graphite");

        variant.setShaftMaterial("Graphite");

        variant.setPlayingLevel("Intermediate");

        variant.setActive(true);

        productVariantRepository.save(variant);
    }
    
    private void seedVariants() {

        // Nitrix Attack
        Product p1 = productRepository.findByName("Nitrix Attack").orElseThrow();
        createVariant(p1, "HND-RKT-001-4U-G5", 3999.0, 3499.0, 15, "4U", "G5");
        createVariant(p1, "HND-RKT-001-5U-G5", 3999.0, 3499.0, 10, "5U", "G5");

        // Flareon Typhoon
        Product p2 = productRepository.findByName("Flareon Typhoon").orElseThrow();
        createVariant(p2, "HND-RKT-002-4U-G5", 3799.0, 3299.0, 12, "4U", "G5");
        createVariant(p2, "HND-RKT-002-5U-G5", 3799.0, 3299.0, 8, "5U", "G5");

        // Powertek 1000 JR
        Product p3 = productRepository.findByName("Powertek 1000 JR").orElseThrow();
        createVariant(p3, "HND-RKT-003-4U-G5", 2999.0, 2599.0, 20, "4U", "G5");
        createVariant(p3, "HND-RKT-003-5U-G5", 2999.0, 2599.0, 15, "5U", "G5");

        // Nitrix 70 Venom
        Product p4 = productRepository.findByName("Nitrix 70 Venom").orElseThrow();
        createVariant(p4, "HND-RKT-004-4U-G5", 4499.0, 3999.0, 10, "4U", "G5");
        createVariant(p4, "HND-RKT-004-5U-G5", 4499.0, 3999.0, 8, "5U", "G5");

        // Power Ray 900
        Product p5 = productRepository.findByName("Power Ray 900").orElseThrow();
        createVariant(p5, "HND-RKT-005-4U-G5", 3499.0, 2999.0, 18, "4U", "G5");
        createVariant(p5, "HND-RKT-005-5U-G5", 3499.0, 2999.0, 12, "5U", "G5");

        // T-Rex 800
        Product p6 = productRepository.findByName("T-Rex 800").orElseThrow();
        createVariant(p6, "HND-RKT-006-4U-G5", 4199.0, 3699.0, 14, "4U", "G5");
        createVariant(p6, "HND-RKT-006-5U-G5", 4199.0, 3699.0, 10, "5U", "G5");
    }
    
    
    
    
}