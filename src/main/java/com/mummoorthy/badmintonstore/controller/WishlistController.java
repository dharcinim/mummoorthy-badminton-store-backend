package com.mummoorthy.badmintonstore.controller;

import java.util.List;
import com.mummoorthy.badmintonstore.dto.AddToWishlistRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mummoorthy.badmintonstore.entity.Wishlist;
import com.mummoorthy.badmintonstore.service.WishlistService;
import com.mummoorthy.badmintonstore.dto.AddToWishlistRequest;
import com.mummoorthy.badmintonstore.dto.WishlistResponse;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    public List<Wishlist> getAllWishlist() {
        return wishlistService.getAllWishlist();
    }

    @GetMapping("/{id}")
    public Wishlist getWishlistById(@PathVariable Long id) {
        return wishlistService.getWishlistById(id);
    }
    
    @GetMapping("/customer/{customerId}")
    public List<WishlistResponse> getWishlistByCustomer(
            @PathVariable Long customerId) {

        return wishlistService.getWishlistByCustomer(customerId);
    }
    
    @PostMapping
    public Wishlist saveWishlist(@RequestBody Wishlist wishlist) {
        return wishlistService.saveWishlist(wishlist);
    }

    @PutMapping("/{id}")
    public Wishlist updateWishlist(@PathVariable Long id,
                                   @RequestBody Wishlist wishlist) {
        return wishlistService.updateWishlist(id, wishlist);
    }

    @DeleteMapping("/{id}")
    public String deleteWishlist(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return "Wishlist item deleted successfully";
    }
    
    
    
    @PostMapping("/add")
    public Wishlist addToWishlist(
            @RequestBody AddToWishlistRequest request) {

        return wishlistService.addToWishlist(
                request.getCustomerId(),
                request.getProductVariantId());
    }
}