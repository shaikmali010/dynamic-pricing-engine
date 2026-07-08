package com.shaik.dynamicpricing.service.strategy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shaik.dynamicpricing.entity.PricingRule;

@Component
public class PricingStrategyFactory {

	private final List<PricingStrategy> pricingStrategies;
	
	public PricingStrategyFactory(List<PricingStrategy> pricingStrategies) {
        this.pricingStrategies = pricingStrategies;
    }
	
	 public PricingStrategy getStrategy(PricingRule rule) {

	        return pricingStrategies.stream()
	                .filter(strategy -> strategy.supports(rule))
	                .findFirst()
	                .orElseThrow(() ->
	                        new IllegalArgumentException(
	                                "No strategy found for rule type: "
	                                        + rule.getRuleType()));
	    }
}
