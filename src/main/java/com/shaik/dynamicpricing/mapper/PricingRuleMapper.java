package com.shaik.dynamicpricing.mapper;

import com.shaik.dynamicpricing.dto.requestDto.PricingRuleRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.PricingRuleResponseDto;
import com.shaik.dynamicpricing.entity.PricingRule;

public class PricingRuleMapper {

	public static PricingRule toEntity(PricingRuleRequestDto requestDto) {
		
		return PricingRule.builder()
				.ruleName(requestDto.getRuleName())
				.ruleType(requestDto.getRuleType())
				.adjustmentType(requestDto.getAdjustmentType())
				.adjustmentValue(requestDto.getAdjustmentValue())
				.priority(requestDto.getPriority())
				.enabled(requestDto.getEnabled())
				.startTime(requestDto.getStartTime())
	            .endTime(requestDto.getEndTime())
	            .minimumStock(requestDto.getMinimumStock())
				.build();
				
	}
	
	public static PricingRuleResponseDto mapToResponse(PricingRule pricingRule) {
		
		return PricingRuleResponseDto.builder()
				.ruleId(pricingRule.getRuleId())
				.ruleName(pricingRule.getRuleName())
				.ruleType(pricingRule.getRuleType())
				.adjustmentType(pricingRule.getAdjustmentType())
				.adjustmentValue(pricingRule.getAdjustmentValue())
				.priority(pricingRule.getPriority())
				.enabled(pricingRule.getEnabled())
				.startTime(pricingRule.getStartTime())
	            .endTime(pricingRule.getEndTime())
	            .minimumStock(pricingRule.getMinimumStock())
				.build();
	}
	
	public static void updatePricingRule(PricingRule pricingRule, PricingRuleRequestDto requestDto) {
		
		pricingRule.setRuleName(requestDto.getRuleName());
		pricingRule.setRuleType(requestDto.getRuleType());
		pricingRule.setAdjustmentType(requestDto.getAdjustmentType());
		pricingRule.setAdjustmentValue(requestDto.getAdjustmentValue());
		pricingRule.setPriority(requestDto.getPriority());
		pricingRule.setEnabled(requestDto.getEnabled());
		pricingRule.setStartTime(requestDto.getStartTime());
		pricingRule.setEndTime(requestDto.getEndTime());
		pricingRule.setMinimumStock(requestDto.getMinimumStock());
		
	}
}
