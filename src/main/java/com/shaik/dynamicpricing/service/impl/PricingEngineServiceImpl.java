package com.shaik.dynamicpricing.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaik.dynamicpricing.dto.responseDto.DynamicPriceResponseDto;
import com.shaik.dynamicpricing.entity.DynamicPrice;
import com.shaik.dynamicpricing.entity.PricingRule;
import com.shaik.dynamicpricing.entity.Product;
import com.shaik.dynamicpricing.exception.ProductNotFoundException;
import com.shaik.dynamicpricing.mapper.DynamicPriceMapper;
import com.shaik.dynamicpricing.repository.DynamicPriceRepository;
import com.shaik.dynamicpricing.repository.PricingRuleRepository;
import com.shaik.dynamicpricing.repository.ProductRepository;
import com.shaik.dynamicpricing.service.Inter.PricingEngineService;
import com.shaik.dynamicpricing.service.strategy.PricingStrategy;
import com.shaik.dynamicpricing.service.strategy.PricingStrategyFactory;
import com.shaik.dynamicpricing.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingEngineServiceImpl implements PricingEngineService{
	
	private final ProductRepository productRepository;
	
	private final PricingRuleRepository pricingRuleRepository;
	
	private final DynamicPriceRepository dynamicPriceRepository;
	
	private final PricingStrategyFactory pricingStrategyFactory;
	
	@Override
	public DynamicPriceResponseDto calculatePrice(Long productId){
		
		log.info("Calculating dynamic price for product id: {}", productId);
		
		 Product product = productRepository.findById(productId)
		            .orElseThrow(() -> {

		                log.error("Product not found with id: {}", productId);

		                return new ProductNotFoundException(
		                        Constants.PRODUCT_NOT_FOUND +
		                        " with id: " + productId);
		            });

		    log.info("Product fetched successfully.");
		    
		    List<PricingRule> pricingRules =
		            pricingRuleRepository.findByEnabledTrueOrderByPriorityAsc();	
		    
		    log.info("Found {} enabled pricing rules.", pricingRules.size());
		    
		    BigDecimal currentPrice = product.getBasePrice();
		    
		    StringBuilder appliedRules = new StringBuilder();
		    
		    for (PricingRule rule : pricingRules) {

		        PricingStrategy strategy =
		                pricingStrategyFactory.getStrategy(rule);

		        BigDecimal previousPrice = currentPrice;
		        
		        currentPrice = strategy.calculatePricing(
		                product,
		                rule,
		                currentPrice);

		        if(previousPrice.compareTo(currentPrice) != 0) {
		        appliedRules.append(rule.getRuleName())
		                    .append(", ");
		        }
		    }
		
		    // Remove last comma
		    String appliedRuleNames = appliedRules.length() > 0
		            ? appliedRules.substring(0, appliedRules.length() - 2)
		            : "No Rules Applied";

		    // Save Dynamic Price
		    DynamicPrice dynamicPrice = DynamicPrice.builder()
		            .product(product)
		            .basePrice(product.getBasePrice())
		            .finalPrice(currentPrice)
		            .appliedRules(appliedRuleNames)
		            .calculatedAt(LocalDateTime.now())
		            .build();

		    DynamicPrice savedPrice = 
		    		dynamicPriceRepository.save(dynamicPrice);

		    log.info("Dynamic price calculated successfully for product id: {}",
		            productId);

		    return DynamicPriceMapper.mapToResponse(savedPrice);
	}

}
