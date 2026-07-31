package com.mummoorthy.badmintonstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.entity.ProductImage;
import com.mummoorthy.badmintonstore.service.ProductImageService;

@RestController
@RequestMapping("/api/product-images")
@CrossOrigin(origins = "*")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public List<ProductImage> getAllImages() {
        return productImageService.getAllImages();
    }

    @GetMapping("/{id}")
    public ProductImage getImageById(@PathVariable Long id) {
        return productImageService.getImageById(id);
    }
    
    @GetMapping("/product/{productId}")
    public List<ProductImage> getImagesByProductId(
            @PathVariable Long productId) {

        return productImageService.getImagesByProductId(productId);
    }

    @PostMapping
    public ProductImage saveImage(@RequestBody ProductImage image) {
        return productImageService.saveImage(image);
    }

    @PutMapping("/{id}")
    public ProductImage updateImage(@PathVariable Long id,
                                    @RequestBody ProductImage image) {
        return productImageService.updateImage(id, image);
    }

    @DeleteMapping("/{id}")
    public String deleteImage(@PathVariable Long id) {
        productImageService.deleteImage(id);
        return "Product Image deleted successfully";
    }
}