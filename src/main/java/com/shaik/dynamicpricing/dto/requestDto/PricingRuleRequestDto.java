package com.shaik.dynamicpricing.dto.requestDto;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.shaik.dynamicpricing.enums.AdjustmentType;
import com.shaik.dynamicpricing.enums.RuleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingRuleRequestDto {

	@NotBlank(message = "Rule name is required")
	private String ruleName;
	
	@NotNull(message = "Rule type is required")
	private RuleType ruleType;
	
	@NotNull(message = "Adjustment Type is required")
	private AdjustmentType adjustmentType;
	
	@NotNull(message = "Adjustment value is required")
	@Positive(message = "Adjustment value must be greater than zero")
	private BigDecimal adjustmentValue;
	
	@NotNull(message = "Priority is required")
	@Positive(message = "Priority must be greater than zero")
	private Integer priority;
	
	@NotNull(message = "Enable status is required")
	private Boolean enabled;
	
	private LocalTime startTime;

	private LocalTime endTime;
	
	private Integer minimumStock;
}
