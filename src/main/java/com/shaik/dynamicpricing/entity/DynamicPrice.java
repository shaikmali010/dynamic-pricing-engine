package com.shaik.dynamicpricing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dynamic_price")
public class DynamicPrice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dynamicPriceId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(nullable = false)
	private BigDecimal basePrice;
	
	@Column(nullable = false)
	private BigDecimal finalPrice;
	
	@Column(nullable = false)
	private String appliedRules;
	
	@Column(nullable = false)
	private LocalDateTime calculatedAt;
	
	@Version
	private Long version;

}
