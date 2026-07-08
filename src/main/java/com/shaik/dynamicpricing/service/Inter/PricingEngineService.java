package com.shaik.dynamicpricing.service.Inter;

import com.shaik.dynamicpricing.dto.responseDto.DynamicPriceResponseDto;

public interface PricingEngineService {

	DynamicPriceResponseDto calculatePrice(Long productId);
}
