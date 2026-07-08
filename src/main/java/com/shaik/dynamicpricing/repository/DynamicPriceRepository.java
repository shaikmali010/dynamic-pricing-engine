package com.shaik.dynamicpricing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shaik.dynamicpricing.entity.DynamicPrice;
import com.shaik.dynamicpricing.entity.Product;

@Repository
public interface DynamicPriceRepository extends JpaRepository<DynamicPrice, Long>{

	List<DynamicPrice> findByProduct(Product product);
}
