package com.shaik.dynamicpricing.mapper;

import com.shaik.dynamicpricing.dto.responseDto.DynamicPriceResponseDto;
import com.shaik.dynamicpricing.entity.DynamicPrice;

public class DynamicPriceMapper {
	
	public static DynamicPriceResponseDto mapToResponse(
			DynamicPrice price) {
		
		return DynamicPriceResponseDto.builder()
				.dynamicPriceId(price.getDynamicPriceId())
				.productId(price.getProduct().getProductId())
				.productName(price.getProduct().getName())
				.basePrice(price.getBasePrice())
				.finalPrice(price.getFinalPrice())
				.appliedRules(price.getAppliedRules())
				.calculatedAt(price.getCalculatedAt())
				.build();
	}

}
