package com.mummoorthy.badmintonstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



import com.mummoorthy.badmintonstore.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	
	Optional<Brand> findByName(String name);

}