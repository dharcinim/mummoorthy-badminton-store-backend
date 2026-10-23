package com.mummoorthy.badmintonstore.repository;
import java.util.List;
import com.mummoorthy.badmintonstore.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mummoorthy.badmintonstore.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	
	List<ProductImage> findByProductIdOrderByDisplayOrderAsc(Long productId);
	boolean existsByProductAndImageUrl(Product product, String imageUrl);


}