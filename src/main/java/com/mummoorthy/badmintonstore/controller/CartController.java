package com.mummoorthy.badmintonstore.controller;

import java.util.List;
import com.mummoorthy.badmintonstore.entity.CartItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.entity.Cart;
import com.mummoorthy.badmintonstore.service.CartService;
import com.mummoorthy.badmintonstore.dto.AddToCartRequest;
import com.mummoorthy.badmintonstore.dto.CartResponseDTO;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/customer/{customerId}")
    public Cart getCartByCustomerId(
            @PathVariable Long customerId) {

        return cartService.getCartByCustomerId(customerId);

    }
    
    @GetMapping("/response/{customerId}")
    public List<CartResponseDTO> getCartResponse(
            @PathVariable Long customerId) {

        return cartService.getCartResponse(customerId);

    }
     
    @PostMapping
    public Cart saveCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id,
                           @RequestBody Cart cart) {
        return cartService.updateCart(id, cart);
    }
    
    @PutMapping("/item/{cartItemId}")
    public CartItem updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {

        return cartService.updateCartItemQuantity(cartItemId, quantity);
    }
    
    @DeleteMapping("/item/{cartItemId}")
    public String removeCartItem(@PathVariable Long cartItemId) {

        cartService.removeCartItem(cartItemId);

        return "Cart item removed successfully";
    }
    
    @DeleteMapping("/customer/{customerId}")
    public String clearCart(@PathVariable Long customerId) {

        cartService.clearCart(customerId);

        return "Cart cleared successfully";
    }
    
    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return "Cart deleted successfully";
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) {
    	
    	System.out.println("Customer ID = " + request.getCustomerId());
    	System.out.println("Variant ID = " + request.getProductVariantId());
    	System.out.println("Quantity = " + request.getQuantity());

        try {

            return ResponseEntity.ok(
                cartService.addToCart(
                    request.getCustomerId(),
                    request.getProductVariantId(),
                    request.getQuantity()
                )
            );

        } catch (Exception e) {

            e.printStackTrace();   // <-- IMPORTANT

            return ResponseEntity.status(500)
                    .body(e.getMessage());
        }
    }
    
}
