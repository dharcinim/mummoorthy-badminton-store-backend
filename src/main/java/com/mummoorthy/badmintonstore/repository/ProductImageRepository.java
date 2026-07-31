package com.mummoorthy.badmintonstore.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mummoorthy.badmintonstore.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	
	List<ProductImage> findByProductIdOrderByDisplayOrderAsc(Long productId);

}