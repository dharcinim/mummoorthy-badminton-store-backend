package com.mummoorthy.badmintonstore.service;

import java.util.List;

import com.mummoorthy.badmintonstore.entity.Brand;

public interface BrandService {

    List<Brand> getAllBrands();

    Brand getBrandById(Long id);

    Brand saveBrand(Brand brand);

    Brand updateBrand(Long id, Brand brand);

    void deleteBrand(Long id);
}