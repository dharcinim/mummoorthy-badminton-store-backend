package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.ProductVariant;

public interface ProductVariantService {

    List<ProductVariant> getAllVariants();

    ProductVariant getVariantById(Long id);
    
    List<ProductVariant> getVariantsByProductId(Long productId);

    ProductVariant saveVariant(ProductVariant variant);

    ProductVariant updateVariant(Long id, ProductVariant variant);

    void deleteVariant(Long id);
}