package com.example.shoplapttop.repository;

import com.example.shoplapttop.entity.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor<Order> {
    @Query(value = "SELECT  (MONTH(c.date_create)) AS month, (YEAR(c.date_create)) AS year,sum(c.order_total) as total FROM shoplap.orders c where (c.date_create between ?1 and ?2) GROUP BY month,year;",nativeQuery = true)
     List<Object[]> find(LocalDate a, LocalDate b);
    @Query(value = "SELECT  (MONTH(c.date_create)) AS month, (YEAR(c.date_create)) AS year,sum(c.order_total) as total FROM shoplap.orders c GROUP BY month,year  ORDER BY month DESC,year DESC;",nativeQuery = true)
    List<Object[]> find1();


}
