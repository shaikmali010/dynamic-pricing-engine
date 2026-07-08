package com.shaik.dynamicpricing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shaik.dynamicpricing.entity.PricingRule;

@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long>{

	List<PricingRule> findByEnabledTrueOrderByPriorityAsc();
	
	 boolean existsByRuleName(String ruleName);

	    Optional<PricingRule> findByRuleName(String ruleName);
}
