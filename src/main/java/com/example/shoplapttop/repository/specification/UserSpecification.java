package com.example.shoplapttop.repository.specification;

import com.example.shoplapttop.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filterUser(String keyword){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("numberPhone"),"%" +keyword+"%"));
            predicates.add(criteriaBuilder.like(root.get("email"),"%" +keyword+"%"));
            predicates.add(criteriaBuilder.like(root.get("userName"),"%" +keyword+"%"));
            predicates.add(criteriaBuilder.equal(root.get("status"),0));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
