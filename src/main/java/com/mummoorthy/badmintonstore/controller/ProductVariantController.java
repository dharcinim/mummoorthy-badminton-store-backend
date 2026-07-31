package com.mummoorthy.badmintonstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.service.ProductVariantService;

@RestController
@RequestMapping("/api/product-variants")
@CrossOrigin(origins = "*")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping
    public List<ProductVariant> getAllVariants() {
        return productVariantService.getAllVariants();
    }

    @GetMapping("/product/{productId}")
    public List<ProductVariant> getVariantsByProductId(
            @PathVariable Long productId) {

        return productVariantService.getVariantsByProductId(productId);
    }

    @PostMapping
    public ProductVariant saveVariant(@RequestBody ProductVariant variant) {
        return productVariantService.saveVariant(variant);
    }

    @PutMapping("/{id}")
    public ProductVariant updateVariant(@PathVariable Long id,
                                        @RequestBody ProductVariant variant) {
        return productVariantService.updateVariant(id, variant);
    }

    @DeleteMapping("/{id}")
    public String deleteVariant(@PathVariable Long id) {
        productVariantService.deleteVariant(id);
        return "Product Variant deleted successfully";
    }
}