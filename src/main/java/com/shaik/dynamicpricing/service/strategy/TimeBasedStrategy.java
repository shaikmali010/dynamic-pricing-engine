package com.shaik.dynamicpricing.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.shaik.dynamicpricing.entity.PricingRule;
import com.shaik.dynamicpricing.entity.Product;
import com.shaik.dynamicpricing.enums.AdjustmentType;
import com.shaik.dynamicpricing.enums.RuleType;

@Component
public class TimeBasedStrategy implements PricingStrategy {

    @Override
    public boolean supports(PricingRule rule) {
        return rule.getRuleType() == RuleType.TIME_BASED;
    }

    @Override
    public BigDecimal calculatePricing(Product product,
                                       PricingRule rule,
                                       BigDecimal currentPrice) {
    	
    	if (rule.getStartTime() == null || rule.getEndTime() == null) {
            return currentPrice;
        }


        LocalTime now = LocalTime.now();

        if(!now.isAfter(rule.getStartTime())
                && !now.isBefore(rule.getEndTime())){

            if(rule.getAdjustmentType() == AdjustmentType.PERCENTAGE){

                BigDecimal adjustmentAmount=currentPrice
                        .multiply(rule.getAdjustmentValue())
                        .divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);

                return currentPrice.add(adjustmentAmount);
            }

            return currentPrice.add(rule.getAdjustmentValue());
        }

        return currentPrice;
    }
}