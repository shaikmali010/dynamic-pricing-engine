package com.shaik.dynamicpricing.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shaik.dynamicpricing.dto.responseDto.DynamicPriceResponseDto;
import com.shaik.dynamicpricing.service.Inter.PricingEngineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pricing-engine")
@Tag(name = "Pricing Engine APIs")
public class PricingEngineController {

    private final PricingEngineService pricingEngineService;

    @PostMapping("/calculate/{productId}")
    @Operation(
    	    summary = "Calculate Dynamic Price",
    	    description = "Calculates the final dynamic price by applying all enabled pricing rules."
    	)
    public ResponseEntity<DynamicPriceResponseDto> calculatePrice(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                pricingEngineService.calculatePrice(productId));
    }
}