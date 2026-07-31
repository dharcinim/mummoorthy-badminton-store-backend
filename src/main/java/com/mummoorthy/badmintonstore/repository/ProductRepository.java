package com.mummoorthy.badmintonstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;


import com.mummoorthy.badmintonstore.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findBySlug(String slug);
	Optional<Product> findByName(String name);
	
	List<Product> findByCategoryId(Long categoryId);
	List<Product> findByCategoryNameIgnoreCase(String categoryName);
	List<Product> findByNameContainingIgnoreCase(String keyword);
	List<Product> findByBrandId(Long brandId);

}