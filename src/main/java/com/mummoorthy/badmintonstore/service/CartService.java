package com.mummoorthy.badmintonstore.service;

import java.util.List;
import com.mummoorthy.badmintonstore.entity.CartItem;
import com.mummoorthy.badmintonstore.dto.CartResponseDTO;

import com.mummoorthy.badmintonstore.entity.Cart;

public interface CartService {

    List<Cart> getAllCarts();

    Cart getCartById(Long id);
    
    Cart getCartByCustomerId(Long customerId);
    
    Cart saveCart(Cart cart);

    Cart updateCart(Long id, Cart cart);
    
    Cart addToCart(Long customerId, Long productVariantId, Integer quantity);
    
    CartItem updateCartItemQuantity(Long cartItemId, Integer quantity);
    
    List<CartResponseDTO> getCartResponse(Long customerId);
    
    void removeCartItem(Long cartItemId);
    
    void deleteCart(Long id);
    
    void clearCart(Long customerId);
}