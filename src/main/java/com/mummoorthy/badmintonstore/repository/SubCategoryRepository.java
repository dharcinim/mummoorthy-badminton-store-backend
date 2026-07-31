package com.mummoorthy.badmintonstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.mummoorthy.badmintonstore.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
	
	Optional<SubCategory> findByName(String name);
	
}