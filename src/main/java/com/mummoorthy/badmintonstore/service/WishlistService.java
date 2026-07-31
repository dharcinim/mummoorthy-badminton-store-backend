package com.mummoorthy.badmintonstore.service;

import java.util.List;
import com.mummoorthy.badmintonstore.dto.WishlistResponse;

import com.mummoorthy.badmintonstore.entity.Wishlist;

public interface WishlistService {

    List<Wishlist> getAllWishlist();
    List<WishlistResponse> getWishlistByCustomer(Long customerId);

    Wishlist getWishlistById(Long id);

    Wishlist saveWishlist(Wishlist wishlist);

    Wishlist updateWishlist(Long id, Wishlist wishlist);
    Wishlist addToWishlist(Long customerId, Long productVariantId);

    void deleteWishlist(Long id);
    
    
}

