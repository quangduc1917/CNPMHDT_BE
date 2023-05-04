package com.example.shoplapttop.repository;

import com.example.shoplapttop.entity.CommentSection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommentSectionRepository extends JpaRepository<CommentSection,Long> , JpaSpecificationExecutor<CommentSection> {}
