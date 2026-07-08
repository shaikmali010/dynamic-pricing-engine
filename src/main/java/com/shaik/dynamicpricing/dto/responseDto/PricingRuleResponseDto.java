package com.shaik.dynamicpricing.dto.responseDto;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.shaik.dynamicpricing.enums.AdjustmentType;
import com.shaik.dynamicpricing.enums.RuleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PricingRuleResponseDto {

	private Long ruleId;
	
	private String ruleName;
	
	private RuleType ruleType;
	
	private AdjustmentType adjustmentType;
	
	private BigDecimal adjustmentValue;
	
	private Integer priority;
	
	private Boolean enabled;
	
	private LocalTime startTime;

    private LocalTime endTime;
    
    private Integer minimumStock;
}
