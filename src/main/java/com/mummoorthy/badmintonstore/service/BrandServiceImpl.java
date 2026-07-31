package com.mummoorthy.badmintonstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mummoorthy.badmintonstore.entity.Brand;
import com.mummoorthy.badmintonstore.repository.BrandRepository;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long id, Brand brand) {
        Brand existing = brandRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setName(brand.getName());
        existing.setLogoUrl(brand.getLogoUrl());
        existing.setDescription(brand.getDescription());
        existing.setActive(brand.getActive());

        return brandRepository.save(existing);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}