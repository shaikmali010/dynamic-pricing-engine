package com.shaik.dynamicpricing.service.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.shaik.dynamicpricing.entity.PricingRule;
import com.shaik.dynamicpricing.entity.Product;
import com.shaik.dynamicpricing.enums.AdjustmentType;
import com.shaik.dynamicpricing.enums.RuleType;

@Component
public class DiscountStrategy implements PricingStrategy {

    @Override
    public boolean supports(PricingRule rule) {
        return rule.getRuleType() == RuleType.DISCOUNT;
    }

    @Override
    public BigDecimal calculatePricing(Product product,
                                       PricingRule rule,
                                       BigDecimal currentPrice) {

        if (rule.getAdjustmentType() == AdjustmentType.PERCENTAGE) {

            BigDecimal discount = currentPrice
                    .multiply(rule.getAdjustmentValue())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            return currentPrice.subtract(discount);
        }

        return currentPrice.subtract(rule.getAdjustmentValue());
    }
}