package com.mummoorthy.badmintonstore.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mummoorthy.badmintonstore.dto.ProductResponseDTO;
import com.mummoorthy.badmintonstore.entity.Product;
import com.mummoorthy.badmintonstore.entity.ProductImage;
import com.mummoorthy.badmintonstore.entity.ProductVariant;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponseDTO(
            Product product,
            List<ProductVariant> variants,
            List<ProductImage> images) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());

        dto.setDescription(product.getDescription());
        dto.setShortDescription(product.getShortDescription());

        dto.setHsnCode(product.getHsnCode());
        dto.setGstPercentage(product.getGstPercentage());

        dto.setWarranty(product.getWarranty());
        dto.setCountryOfOrigin(product.getCountryOfOrigin());

        dto.setFeatured(product.getFeatured());
        dto.setBestSeller(product.getBestSeller());
        dto.setNewArrival(product.getNewArrival());
        dto.setActive(product.getActive());

        dto.setBrandName(
        	    product.getBrand() != null
        	        ? product.getBrand().getName()
        	        : null
        	);

        	dto.setCategoryName(
        	    product.getCategory() != null
        	        ? product.getCategory().getName()
        	        : null
        	);
        dto.setSubCategoryName(
        	    product.getSubCategory() != null
        	        ? product.getSubCategory().getName()
        	        : null
        	);

        // First Variant
        if (!variants.isEmpty()) {

            ProductVariant variant = variants.get(0);

            dto.setMrp(variant.getMrp());
            dto.setSellingPrice(variant.getSellingPrice());
            dto.setStock(variant.getStock());
        }

        // Primary Image
        if (!images.isEmpty()) {

            dto.setImageUrl(images.get(0).getImageUrl());
        }

        return dto;
    }
}