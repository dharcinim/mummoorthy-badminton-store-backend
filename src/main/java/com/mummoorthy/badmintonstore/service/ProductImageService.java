package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.ProductImage;

public interface ProductImageService {

    List<ProductImage> getAllImages();
    List<ProductImage> getImagesByProductId(Long productId);

    ProductImage getImageById(Long id);

    ProductImage saveImage(ProductImage image);

    ProductImage updateImage(Long id, ProductImage image);

    void deleteImage(Long id);
}