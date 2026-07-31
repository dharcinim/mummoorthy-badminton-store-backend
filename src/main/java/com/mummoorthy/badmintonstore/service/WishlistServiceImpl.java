package com.mummoorthy.badmintonstore.service;

import java.util.List;
import com.mummoorthy.badmintonstore.dto.WishlistResponse;
import com.mummoorthy.badmintonstore.entity.ProductImage;
import java.util.ArrayList;
import java.util.Optional;

import com.mummoorthy.badmintonstore.entity.Customer;
import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.repository.CustomerRepository;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.Wishlist;
import com.mummoorthy.badmintonstore.repository.WishlistRepository;
import com.mummoorthy.badmintonstore.repository.ProductImageRepository;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public List<Wishlist> getAllWishlist() {
        return wishlistRepository.findAll();
    }

    @Override
    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    @Override
    public Wishlist saveWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;
    
    @Autowired
    private ProductImageRepository productImageRepository;
    
    @Override
    public Wishlist addToWishlist(Long customerId, Long productVariantId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new RuntimeException("Product Variant not found"));

        Optional<Wishlist> existingWishlist =
                wishlistRepository.findByCustomerIdAndProductVariantId(
                        customerId,
                        productVariantId);

        if (existingWishlist.isPresent()) {
            return existingWishlist.get();
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        wishlist.setProductVariant(productVariant);

        return wishlistRepository.save(wishlist);
    }
    
    @Override
    public Wishlist updateWishlist(Long id, Wishlist wishlist) {

        Wishlist existingWishlist = wishlistRepository.findById(id).orElse(null);

        if (existingWishlist != null) {

            existingWishlist.setAddedAt(wishlist.getAddedAt());
            existingWishlist.setCustomer(wishlist.getCustomer());
            existingWishlist.setProductVariant(wishlist.getProductVariant());

            return wishlistRepository.save(existingWishlist);
        }

        return null;
    }
    
    @Override
    public List<WishlistResponse> getWishlistByCustomer(Long customerId) {

        List<Wishlist> wishlistItems =
                wishlistRepository.findByCustomerId(customerId);

        List<WishlistResponse> response = new ArrayList<>();

        for (Wishlist item : wishlistItems) {

            WishlistResponse dto = new WishlistResponse();

            dto.setWishlistId(item.getId());

            dto.setProductId(
                    item.getProductVariant().getProduct().getId());

            dto.setProductVariantId(
                    item.getProductVariant().getId());

            dto.setProductName(
                    item.getProductVariant().getProduct().getName());

            dto.setSellingPrice(
                    item.getProductVariant().getSellingPrice());

            dto.setMrp(
                    item.getProductVariant().getMrp());

            dto.setBrandName(
                    item.getProductVariant().getProduct().getBrand().getName());

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
    public void deleteWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }
}