package com.shaik.dynamicpricing.entity;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.shaik.dynamicpricing.enums.AdjustmentType;
import com.shaik.dynamicpricing.enums.RuleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pricing_rules")
public class PricingRule extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ruleId;
	
	@Column(nullable = false, unique = true)
	private String ruleName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RuleType ruleType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AdjustmentType adjustmentType;
	
	@Column(nullable = false)
	private BigDecimal adjustmentValue;
	
	@Column(nullable = false)
	private Integer priority;
	
	@Column(nullable = false)
	private Boolean enabled;
	
	@Column(nullable = true)
	private LocalTime startTime;

	@Column(nullable = true)
	private LocalTime endTime;
	
	@Column(nullable = true)
	private Integer minimumStock;
	
	@Version
	private Long version;
	

}
