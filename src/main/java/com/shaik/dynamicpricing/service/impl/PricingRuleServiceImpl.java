package com.shaik.dynamicpricing.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaik.dynamicpricing.dto.requestDto.PricingRuleRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.PricingRuleResponseDto;
import com.shaik.dynamicpricing.entity.PricingRule;
import com.shaik.dynamicpricing.enums.RuleType;
import com.shaik.dynamicpricing.enums.SortDirection;
import com.shaik.dynamicpricing.exception.DuplicatePricingRuleException;
import com.shaik.dynamicpricing.exception.PricingRuleNotFoundException;
import com.shaik.dynamicpricing.mapper.PricingRuleMapper;
import com.shaik.dynamicpricing.repository.PricingRuleRepository;
import com.shaik.dynamicpricing.service.Inter.PricingRuleService;
import com.shaik.dynamicpricing.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PricingRuleServiceImpl implements PricingRuleService {

	private final PricingRuleRepository pricingRuleRepository;
	
	private void validatePricingRule(PricingRuleRequestDto requestDto) {

	    if (requestDto.getRuleType() == RuleType.TIME_BASED) {

	        if (requestDto.getStartTime() == null
	                || requestDto.getEndTime() == null) {

	            throw new IllegalArgumentException(
	                    "Start time and End time are required for TIME_BASED rule.");
	        }

	        if (!requestDto.getStartTime().isBefore(requestDto.getEndTime())) {

	            throw new IllegalArgumentException(
	                    "Start time must be before End time.");
	        }
	    }

	    if (requestDto.getRuleType() == RuleType.INVENTORY) {

	        if (requestDto.getMinimumStock() == null) {

	            throw new IllegalArgumentException(
	                    "Minimum stock is required for INVENTORY rule.");
	        }

	        if (requestDto.getMinimumStock() < 0) {

	            throw new IllegalArgumentException(
	                    "Minimum stock cannot be negative.");
	        }
	    }
	}
	
	@Override
	public PricingRuleResponseDto addPricingRule(
			PricingRuleRequestDto requestDto) {
		
		log.info("Adding Pricing rule with name: {}", requestDto.getRuleName());
		
		if(pricingRuleRepository.existsByRuleName(requestDto.getRuleName())) {
			
			log.warn("Pricing rule already exists with name: {}", 
					requestDto.getRuleName());
			
			throw new DuplicatePricingRuleException(
					Constants.PRICING_RULE_ALREADY_EXISTS 
					+ " with name: "+requestDto.getRuleName());
		}
		
		validatePricingRule(requestDto);
		
		PricingRule pricingRule = 
				PricingRuleMapper.toEntity(requestDto);
		
		PricingRule savedRule = pricingRuleRepository.save(pricingRule);
		
		log.info("Pricing rule created successfully with id: {}",
                savedRule.getRuleId());
		
		return PricingRuleMapper.mapToResponse(savedRule);
	}
	
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "pricingRule", key = "#ruleId")
	public PricingRuleResponseDto getPricingRuleById(Long ruleId) {
		
		log.info("Fetching pricing rule with id: {}", ruleId);
		
		PricingRule pricingRule = pricingRuleRepository.findById(ruleId)
				         .orElseThrow(() -> {
				        	 
				        	 log.error("Pricing rule not found with id: {}", ruleId);
				        	
				        	 return new PricingRuleNotFoundException(
				        			 Constants.PRICING_RULE_NOT_FOUND 
				        			 + " with id: "+ruleId);
				         });
		
		log.info("Pricing rule fetched successfully by id: {}", ruleId);
		
		return PricingRuleMapper.mapToResponse(pricingRule);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<PricingRuleResponseDto> getAllPricingRules(int page, int size, 
			String sortBy, SortDirection direction){
		
		log.info("Fetching all pricing rule.");
		
		Sort sort = direction == SortDirection.ASC
				? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size, sort);
		
		log.info("Fetching pricing rule with page: {}, size: {}, sortBy : {}, sortDir: {}",
				page, size, sortBy, direction);
		
		Page<PricingRule> pricingRules = pricingRuleRepository.findAll(pageable);
		
		log.info("The pricing rule fetched successfully.");
		
		return pricingRules.map(PricingRuleMapper::mapToResponse);
	}
	
	@Override
	@CachePut(value = "pricingRule", key = "#ruleId")
	public PricingRuleResponseDto updatePricingRule(Long ruleId, 
			PricingRuleRequestDto requestDto) {
		
		log.info("Updating the Pricing rule with id: {}", ruleId);
		
		PricingRule pricingRule = pricingRuleRepository.findById(ruleId)
							.orElseThrow(() -> {
								
								log.error("Pricing rule not found with id: {}", ruleId);
								
								throw new PricingRuleNotFoundException(
										Constants.PRICING_RULE_NOT_FOUND 
										+ " with id: "+ruleId);
							});
		
		if(!pricingRule.getRuleName().equals(requestDto.getRuleName())) {
			
			pricingRuleRepository.findByRuleName(requestDto.getRuleName())
								.ifPresent(rule -> {
									
									log.warn("Pricing rule already exists with name: {}", 
											requestDto.getRuleName());
									
									throw new DuplicatePricingRuleException(
					Constants.PRICING_RULE_ALREADY_EXISTS  
					+ " with name: " + requestDto.getRuleName());
				});
		
		}
		
		validatePricingRule(requestDto);
		
		PricingRuleMapper.updatePricingRule(pricingRule, requestDto);
		
		PricingRule updatedRule = pricingRuleRepository.save(pricingRule);
		
		log.info("Pricing rule updated successfully with id: {}", ruleId);
		
		return PricingRuleMapper.mapToResponse(updatedRule);
	}
	
	@Override
	@CacheEvict(value = "pricingRule", key = "#ruleId")
	public void deletePricingRule(Long ruleId) {
		
		log.info("Delete the Pricing rule with id: {}", ruleId);
		
		PricingRule pricingRule = pricingRuleRepository.findById(ruleId)
		.orElseThrow(() -> {
			
			log.error("Pricing rule not found with id: {}", ruleId);
			
			return new PricingRuleNotFoundException(
					Constants.PRICING_RULE_NOT_FOUND 
					+ " with id: "+ruleId);
		});
		
		pricingRuleRepository.delete(pricingRule);
		
		log.info("Pricing rule deleted successfully with id: {}", ruleId);
	}
	
	@Override
	@CachePut(value = "pricingRule", key = "#ruleId")
	public PricingRuleResponseDto enableRule(Long ruleId) {
		
		log.info("Enabling the Pricing rule with id: {}", ruleId);
		
		PricingRule pricingRule = pricingRuleRepository.findById(ruleId)
								.orElseThrow(() -> {
									
									log.error("Pricing rule not found with id: {}", ruleId);
									
									return new PricingRuleNotFoundException(
											Constants.PRICING_RULE_NOT_FOUND 
											+ " with id: " + ruleId);
								});
		
		if(Boolean.TRUE.equals(pricingRule.getEnabled())) {
			return PricingRuleMapper.mapToResponse(pricingRule);
		}
		
		pricingRule.setEnabled(true);
		
		PricingRule updatedRule = pricingRuleRepository.save(pricingRule);
		
		log.info("Pricing rule enabled successfully with id: {}", ruleId);
		
		return PricingRuleMapper.mapToResponse(updatedRule);
	}
	
	@Override
	@CachePut(value = "pricingRule", key = "#ruleId")
	public PricingRuleResponseDto disableRule(Long ruleId) {
		
		log.info("Disabling pricing rule with id: {}", ruleId);
		
		PricingRule pricingRule = pricingRuleRepository.findById(ruleId)
									.orElseThrow(() -> {
										
										log.error("Pricing rule not found with id: {}", ruleId);
										
										return new PricingRuleNotFoundException(
												Constants.PRICING_RULE_NOT_FOUND 
												+ " with id: "+ ruleId);
									});
		
		if(Boolean.FALSE.equals(pricingRule.getEnabled())){
			return PricingRuleMapper.mapToResponse(pricingRule);
		}
		
		pricingRule.setEnabled(false);
		
		PricingRule updatedPricingRule = pricingRuleRepository.save(pricingRule);
		
		log.info("Pricing rule disabled successfully with id: {}", ruleId);
		
		return PricingRuleMapper.mapToResponse(updatedPricingRule);
	}
	
}
