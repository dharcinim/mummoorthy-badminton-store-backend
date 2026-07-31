package com.mummoorthy.badmintonstore.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mummoorthy.badmintonstore.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	
	

    Optional<Wishlist> findByCustomerIdAndProductVariantId(
            Long customerId,
            Long productVariantId);
    
    List<Wishlist> findByCustomerId(Long customerId);
    

}