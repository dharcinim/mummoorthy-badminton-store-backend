package com.mummoorthy.badmintonstore.service;


import java.util.List;
import com.mummoorthy.badmintonstore.dto.ProductResponseDTO;
import com.mummoorthy.badmintonstore.entity.Product;

public interface ProductService {

	List<ProductResponseDTO> getAllProducts();
    List<ProductResponseDTO> getProductsByCategory(String categoryName);
    List<Product> searchProducts(String keyword);
    Product getProductById(Long id);

    Product saveProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
}