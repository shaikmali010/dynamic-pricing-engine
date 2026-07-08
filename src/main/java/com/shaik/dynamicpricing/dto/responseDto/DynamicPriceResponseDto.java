package com.shaik.dynamicpricing.dto.responseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DynamicPriceResponseDto {
	
	private Long dynamicPriceId;
	
	private Long productId;
	
	private String productName;
	
	private BigDecimal basePrice;
	
	private BigDecimal finalPrice;
	
	private String appliedRules;
	
	private LocalDateTime calculatedAt;
	

}
