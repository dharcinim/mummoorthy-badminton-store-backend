package com.mummoorthy.badmintonstore.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mummoorthy.badmintonstore.entity.Product;
import com.mummoorthy.badmintonstore.entity.ProductImage;
import com.mummoorthy.badmintonstore.repository.ProductImageRepository;
import com.mummoorthy.badmintonstore.repository.ProductRepository;

//@Component
public class ProductImageSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public ProductImageSeeder(ProductRepository productRepository,
                              ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public void run(String... args) {

        if (productImageRepository.count() > 0) {
            return;
        }

        seedImages();
    }

    private void addImage(Product product,
                          String imageUrl,
                          int displayOrder,
                          boolean primary) {

        ProductImage image = new ProductImage();

        image.setProduct(product);
        image.setImageUrl(imageUrl);
        image.setDisplayOrder(displayOrder);
        image.setPrimaryImage(primary);

        productImageRepository.save(image);
    }

    private void seedImages() {

        // We will add the images here
    	
    	Product nitrix =
    	        productRepository.findByName("Nitrix Attack").orElseThrow();

    	addImage(nitrix,
    	"/assets/Racket/Nitrix-Attack-5U.webp",
    	1,
    	true);

    	addImage(nitrix,
    	"/assets/Racket/Nitrix-Attack-5U-S1.webp",
    	2,
    	false);

    	addImage(nitrix,
    	"/assets/Racket/Nitrix-Attack-5U-S2.webp",
    	3,
    	false);

    	addImage(nitrix,
    	"/assets/Racket/Nitrix-Attack-5U-S3.webp",
    	4,
    	false);

    	addImage(nitrix,
    	"/assets/Racket/Nitrix-Attack-5U-S4.webp",
    	5,
    	false);

    	addImage(nitrix,
    	"/assets/Racket/Nitrix-Attack-5U-S5.webp",
    	6,
    	false);
    }
}