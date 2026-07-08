package com.shaik.dynamicpricing.service.Inter;

import org.springframework.data.domain.Page;

import com.shaik.dynamicpricing.dto.requestDto.ProductRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.ProductResponseDto;
import com.shaik.dynamicpricing.enums.SortDirection;

public interface ProductService {
	
	ProductResponseDto addProduct(ProductRequestDto requestDto);
	
	ProductResponseDto getProductById(Long productId);
	
	Page<ProductResponseDto> getAllProducts(int page, int size, 
			String sorBy, SortDirection diection);
	
	ProductResponseDto updateProduct(Long ProductId, ProductRequestDto requestDto);
	
	void deleteProduct(Long productId);

}
