package com.shaik.dynamicpricing.dto.responseDto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

	private Long productId;
	
	private String name;
	
	private BigDecimal basePrice;
	
	private Integer stock;
	
	private String category;
	
}
