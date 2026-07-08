package com.shaik.dynamicpricing.service.strategy;

import java.math.BigDecimal;

import com.shaik.dynamicpricing.entity.PricingRule;
import com.shaik.dynamicpricing.entity.Product;

public interface PricingStrategy {

	boolean supports(PricingRule rule);
	
	BigDecimal calculatePricing(
			Product product,
			PricingRule rule,
			BigDecimal currentPrice);
	
}
