package com.mummoorthy.badmintonstore.service;

import java.util.List;
import java.util.ArrayList;

import com.mummoorthy.badmintonstore.dto.ProductResponseDTO;
import com.mummoorthy.badmintonstore.entity.ProductImage;
import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.mapper.ProductMapper;
import com.mummoorthy.badmintonstore.repository.ProductImageRepository;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.Product;
import com.mummoorthy.badmintonstore.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductMapper productMapper;



    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {

            existingProduct.setName(product.getName());
            existingProduct.setSlug(product.getSlug());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setShortDescription(product.getShortDescription());
            existingProduct.setHsnCode(product.getHsnCode());
            existingProduct.setGstPercentage(product.getGstPercentage());
            existingProduct.setWarranty(product.getWarranty());
            existingProduct.setCountryOfOrigin(product.getCountryOfOrigin());
            existingProduct.setFeatured(product.getFeatured());
            existingProduct.setBestSeller(product.getBestSeller());
            existingProduct.setNewArrival(product.getNewArrival());
            existingProduct.setActive(product.getActive());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setSubCategory(product.getSubCategory());

            return productRepository.save(existingProduct);
        }

        return null;
    }
    
    @Override
    public List<ProductResponseDTO> getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductResponseDTO> response = new ArrayList<>();

        for (Product product : products) {

            List<ProductVariant> variants =
                    productVariantRepository.findByProductId(product.getId());

            List<ProductImage> images =
                    productImageRepository.findByProductIdOrderByDisplayOrderAsc(product.getId());

            response.add(
                    productMapper.toResponseDTO(product, variants, images)
            );
        }

        return response;
    }
    
    @Override
    public List<ProductResponseDTO> getProductsByCategory(String categoryName) {

        List<Product> products =
                productRepository.findByCategoryNameIgnoreCase(categoryName);

        List<ProductResponseDTO> response = new ArrayList<>();

        for (Product product : products) {

            List<ProductVariant> variants =
                    productVariantRepository.findByProductId(product.getId());

            List<ProductImage> images =
                    productImageRepository.findByProductIdOrderByDisplayOrderAsc(product.getId());

            response.add(
                    productMapper.toResponseDTO(product, variants, images)
            );
        }

        return response;
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {

        return productRepository
                .findByNameContainingIgnoreCase(keyword);

    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    
    
    
    
}