package com.shaik.dynamicpricing.Controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shaik.dynamicpricing.dto.requestDto.PricingRuleRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.PricingRuleResponseDto;
import com.shaik.dynamicpricing.enums.SortDirection;
import com.shaik.dynamicpricing.service.Inter.PricingRuleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pricing-rules")
@RequiredArgsConstructor
@Validated
@Tag(name = "Pricing Rule APIs",
	 description = "APIs for managing price rules"
	 )
public class PricingRuleController {

	private final PricingRuleService pricingRuleService;
	
	@PostMapping
	@Operation(summary = "Add pricing rule")
	public ResponseEntity<PricingRuleResponseDto> addPricingRule(
			@Valid
			@RequestBody
			PricingRuleRequestDto requestDto){
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(pricingRuleService.addPricingRule(requestDto));
	}
	
	@GetMapping("/{ruleId}")
	@Operation(summary = "Get pricing rule by id")
	public ResponseEntity<PricingRuleResponseDto> getPricingRuleById(
			@PathVariable Long ruleId) {
		return ResponseEntity.ok(pricingRuleService
				.getPricingRuleById(ruleId));
	}
	
	@GetMapping
	@Operation(summary = "Get all pricing rules")
	public ResponseEntity<Page<PricingRuleResponseDto>> getAllPricingRules(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "ruleId") String sortBy,
			@RequestParam(defaultValue = "ASC") SortDirection direction){
		
		return ResponseEntity.ok(pricingRuleService
				.getAllPricingRules(page, size, sortBy, direction));
	}
	
	@PutMapping("/{ruleId}")
	@Operation(summary = "Update the Pricing rule")
	public ResponseEntity<PricingRuleResponseDto> 
			updatePricingRule(
							@PathVariable Long ruleId, 
							@Valid @RequestBody PricingRuleRequestDto requestDto){
		return ResponseEntity.ok(pricingRuleService.updatePricingRule(ruleId, requestDto));
	}
	
	@DeleteMapping("/{ruleId}")
	@Operation(summary = "Delete the Pricing rule by id")
	public ResponseEntity<String> 
			deletePricingRule(@PathVariable Long ruleId){
		pricingRuleService.deletePricingRule(ruleId);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{ruleId}/enable")
	@Operation(summary = "Enable Pricing Rule")
	public ResponseEntity<PricingRuleResponseDto> 
			enableRule(@PathVariable Long ruleId){
		
		return ResponseEntity.ok(pricingRuleService.enableRule(ruleId));
	}
	
	@PatchMapping("/{ruleId}/disable")
	@Operation(summary = "Disable Pricing Rule")
	public ResponseEntity<PricingRuleResponseDto> 
			disableRule(@PathVariable Long ruleId){
		
	return ResponseEntity.ok(pricingRuleService
			.disableRule(ruleId));	
	}
	
	
}
