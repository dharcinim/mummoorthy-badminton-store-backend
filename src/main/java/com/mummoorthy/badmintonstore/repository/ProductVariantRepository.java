package com.mummoorthy.badmintonstore.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
	
	List<ProductVariant> findByProductId(Long productId);
	Optional<ProductVariant> findBySku(String sku);

}