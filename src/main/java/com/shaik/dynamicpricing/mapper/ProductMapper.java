package com.shaik.dynamicpricing.mapper;

import com.shaik.dynamicpricing.dto.requestDto.ProductRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.ProductResponseDto;
import com.shaik.dynamicpricing.entity.Product;

public class ProductMapper {
	
	public static Product toEntity(ProductRequestDto requestDto) {
		
		return Product.builder()
				.name(requestDto.getName())
				.basePrice(requestDto.getBasePrice())
				.stock(requestDto.getStock())
				.category(requestDto.getCategory())
				.build();
	}
	
	public static ProductResponseDto mapToResponse(Product product) {
		
		return ProductResponseDto.builder()
				.productId(product.getProductId())
				.name(product.getName())
				.basePrice(product.getBasePrice())
				.stock(product.getStock())
				.category(product.getCategory())
				.build();
	}
	
	public static void updateProduct(Product product, 
			ProductRequestDto dto) {
		
		
		product.setName(dto.getName());
		product.setBasePrice(dto.getBasePrice());
		product.setStock(dto.getStock());
		product.setCategory(dto.getCategory());
		}

}
