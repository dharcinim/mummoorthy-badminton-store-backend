package com.mummoorthy.badmintonstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    public List<ProductVariant> getAllVariants() {
        return productVariantRepository.findAll();
    }

    @Override
    public ProductVariant getVariantById(Long id) {
        return productVariantRepository.findById(id).orElse(null);
    }

    @Override
    public ProductVariant saveVariant(ProductVariant variant) {
        return productVariantRepository.save(variant);
    }

    @Override
    public ProductVariant updateVariant(Long id, ProductVariant variant) {

        ProductVariant existing = productVariantRepository.findById(id).orElse(null);

        if (existing != null) {

            existing.setSku(variant.getSku());
            existing.setBarcode(variant.getBarcode());
            existing.setMrp(variant.getMrp());
            existing.setSellingPrice(variant.getSellingPrice());
            existing.setStock(variant.getStock());
            existing.setSize(variant.getSize());
            existing.setColor(variant.getColor());
            existing.setWeight(variant.getWeight());
            existing.setGripSize(variant.getGripSize());
            existing.setGauge(variant.getGauge());
            existing.setSpeed(variant.getSpeed());
            existing.setLength(variant.getLength());
            existing.setActive(variant.getActive());
            existing.setProduct(variant.getProduct());

            return productVariantRepository.save(existing);
        }

        return null;
    }
    
    @Override
    public List<ProductVariant> getVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    @Override
    public void deleteVariant(Long id) {
        productVariantRepository.deleteById(id);
    }
}