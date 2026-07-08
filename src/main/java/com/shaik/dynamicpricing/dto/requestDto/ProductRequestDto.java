package com.shaik.dynamicpricing.dto.requestDto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
	
	@NotBlank(message = "Product name is required")
	private String name;
	
	@NotNull(message = "Base price is requred")
	@Positive(message = "Base price mustbe greater than zero")
	private BigDecimal basePrice;
	
	@NotNull(message = "stock is required")
	@Min(value = 0, message = "Stock cannot be negative")
	private Integer stock;
	
	@NotBlank(message = "Category is required")
	private String category;

}
