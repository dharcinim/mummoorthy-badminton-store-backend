package com.mummoorthy.badmintonstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.ProductImage;
import com.mummoorthy.badmintonstore.repository.ProductImageRepository;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<ProductImage> getAllImages() {
        return productImageRepository.findAll();
    }

    @Override
    public ProductImage getImageById(Long id) {
        return productImageRepository.findById(id).orElse(null);
    }

    @Override
    public ProductImage saveImage(ProductImage image) {
        return productImageRepository.save(image);
    }
    
    @Override
    public List<ProductImage> getImagesByProductId(Long productId) {
        return productImageRepository
                .findByProductIdOrderByDisplayOrderAsc(productId);
    }

    @Override
    public ProductImage updateImage(Long id, ProductImage image) {

        ProductImage existing = productImageRepository.findById(id).orElse(null);

        if (existing != null) {

            existing.setImageUrl(image.getImageUrl());
            existing.setAltText(image.getAltText());
            existing.setDisplayOrder(image.getDisplayOrder());
            existing.setPrimaryImage(image.getPrimaryImage());
            existing.setProduct(image.getProduct());

            return productImageRepository.save(existing);
        }

        return null;
    }

    @Override
    public void deleteImage(Long id) {
        productImageRepository.deleteById(id);
    }
}