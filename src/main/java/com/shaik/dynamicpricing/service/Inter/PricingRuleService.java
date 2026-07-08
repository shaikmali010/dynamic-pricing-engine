package com.shaik.dynamicpricing.service.Inter;

import org.springframework.data.domain.Page;

import com.shaik.dynamicpricing.dto.requestDto.PricingRuleRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.PricingRuleResponseDto;
import com.shaik.dynamicpricing.enums.SortDirection;

public interface PricingRuleService {

	PricingRuleResponseDto addPricingRule(PricingRuleRequestDto requestDto);
	
	PricingRuleResponseDto getPricingRuleById(Long ruleId);
	
	Page<PricingRuleResponseDto> getAllPricingRules(int page, int size, 
			String sortBy, SortDirection direction);
	
	PricingRuleResponseDto updatePricingRule(Long ruleId, 
			PricingRuleRequestDto requestDto);
	
	void deletePricingRule(Long ruleId);
	
	PricingRuleResponseDto enableRule(Long ruleId);
	
	PricingRuleResponseDto disableRule(Long ruleId);
	
}
