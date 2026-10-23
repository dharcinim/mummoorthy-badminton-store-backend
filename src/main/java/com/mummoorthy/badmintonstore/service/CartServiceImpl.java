package com.mummoorthy.badmintonstore.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.Cart;
import com.mummoorthy.badmintonstore.repository.CartRepository;
import com.mummoorthy.badmintonstore.entity.Customer;
import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.entity.CartItem;
import com.mummoorthy.badmintonstore.dto.CartResponseDTO;
import com.mummoorthy.badmintonstore.entity.ProductImage;
import com.mummoorthy.badmintonstore.repository.ProductImageRepository;
import com.mummoorthy.badmintonstore.repository.CustomerRepository;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;
import com.mummoorthy.badmintonstore.repository.CartItemRepository;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductImageRepository productImageRepository;
    
    @Override
    public List<CartResponseDTO> getCartResponse(Long customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartResponseDTO> response = new ArrayList<>();

        for (CartItem item : cart.getItems()) {

            CartResponseDTO dto = new CartResponseDTO();

            dto.setCartItemId(item.getId());

            dto.setProductVariantId(item.getProductVariant().getId());

            dto.setProductId(item.getProductVariant().getProduct().getId());

            dto.setProductName(item.getProductVariant().getProduct().getName());

            dto.setSellingPrice(item.getProductVariant().getSellingPrice());

            dto.setWeight(item.getProductVariant().getWeight());

            dto.setQuantity(item.getQuantity());

            dto.setTotalPrice(
                    item.getProductVariant().getSellingPrice()
                            * item.getQuantity());

            List<ProductImage> images =
                    productImageRepository.findByProductIdOrderByDisplayOrderAsc(
                            item.getProductVariant().getProduct().getId());

            if (!images.isEmpty()) {

                dto.setImageUrl(images.get(0).getImageUrl());

            }

            response.add(dto);

        }

        return response;
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {

        Cart existingCart = cartRepository.findById(id).orElse(null);

        if (existingCart != null) {

            existingCart.setCustomer(cart.getCustomer());
            existingCart.setCreatedAt(cart.getCreatedAt());
            existingCart.setUpdatedAt(cart.getUpdatedAt());

            return cartRepository.save(existingCart);
        }

        return null;
    }
    
    @Override
    public Cart getCartByCustomerId(Long customerId) {

        return cartRepository
                .findByCustomerId(customerId)
                .orElse(null);

    }
    
    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) {

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart Item not found"));

        ProductVariant variant = item.getProductVariant();

        if (quantity > variant.getStock()) {
            throw new RuntimeException(
            		"Maximum available stock reached."
            );
        }

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }
    
    @Override
    public void removeCartItem(Long cartItemId) {

        cartItemRepository.deleteById(cartItemId);

    }
    
    @Override
    public void clearCart(Long customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteAll(cart.getItems());

    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
    
    @Override
    public Cart addToCart(Long customerId, Long productVariantId, Integer quantity) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new RuntimeException("Product Variant not found"));

        Cart cart = cartRepository.findByCustomerId(customerId).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cart = cartRepository.save(cart);
        }

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductVariantId(
                        cart.getId(),
                        productVariantId);

        
        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            int requestedQuantity = item.getQuantity() + quantity;

            if (requestedQuantity > productVariant.getStock()) {
                throw new RuntimeException(
                		 "Maximum available stock reached."
                );
            }

            item.setQuantity(requestedQuantity);

            cartItemRepository.save(item);

        } else {

            if (quantity > productVariant.getStock()) {
                throw new RuntimeException(
                		 "Maximum available stock reached."
                );
            }

            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProductVariant(productVariant);
            item.setQuantity(quantity);

            cartItemRepository.save(item);
        }
        
        
        
        return cart;
    }
}