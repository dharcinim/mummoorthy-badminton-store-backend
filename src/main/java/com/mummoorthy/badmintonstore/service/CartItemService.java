package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.CartItem;

public interface CartItemService {

    List<CartItem> getAllCartItems();

    CartItem getCartItemById(Long id);

    CartItem saveCartItem(CartItem cartItem);

    CartItem updateCartItem(Long id, CartItem cartItem);

    void deleteCartItem(Long id);
}