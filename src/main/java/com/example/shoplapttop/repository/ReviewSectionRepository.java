package com.example.shoplapttop.repository;

import com.example.shoplapttop.entity.ReviewSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReviewSectionRepository extends JpaRepository<ReviewSection,Long>, JpaSpecificationExecutor<ReviewSection> {

}
