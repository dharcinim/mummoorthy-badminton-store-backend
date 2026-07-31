package com.mummoorthy.badmintonstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.CartItem;
import com.mummoorthy.badmintonstore.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long id, CartItem cartItem) {

        CartItem existingItem = cartItemRepository.findById(id).orElse(null);

        if (existingItem != null) {

            existingItem.setCart(cartItem.getCart());
            existingItem.setProductVariant(cartItem.getProductVariant());
            existingItem.setQuantity(cartItem.getQuantity());

            return cartItemRepository.save(existingItem);
        }

        return null;
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}