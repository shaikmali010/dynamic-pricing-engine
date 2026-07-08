package com.shaik.dynamicpricing.service.impl;

import com.shaik.dynamicpricing.repository.ProductRepository;
import com.shaik.dynamicpricing.service.Inter.ProductService;
import com.shaik.dynamicpricing.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaik.dynamicpricing.dto.requestDto.ProductRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.ProductResponseDto;
import com.shaik.dynamicpricing.entity.Product;
import com.shaik.dynamicpricing.enums.SortDirection;
import com.shaik.dynamicpricing.exception.DuplicateProductException;
import com.shaik.dynamicpricing.exception.ProductNotFoundException;
import com.shaik.dynamicpricing.mapper.ProductMapper;



@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

	private final ProductRepository productRepository;
	
	@Override
	@Transactional
	public ProductResponseDto addProduct(ProductRequestDto requestDto) {
		
		log.info("Creating product with name: {}", requestDto.getName());
		
		if(productRepository.existsByName(requestDto.getName())) {
			
			log.warn("Product already exists with name: {}", requestDto.getName());
			
			throw new DuplicateProductException(Constants.PRODUCT_ALREADY_EXISTS 
					+" with name: " + requestDto.getName());
		}
			
			Product product = ProductMapper.toEntity(requestDto);
			
			Product savedProduct = productRepository.save(product);
			
			log.info("Product created successfully with id: {}", savedProduct.getProductId());;
			
			return ProductMapper.mapToResponse(savedProduct);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "products", key = "#productId")
	public ProductResponseDto getProductById(Long productId) {
		
		log.info("Fetching the product by Id: {}", productId);
		
		Product product = productRepository.findById(productId)
							.orElseThrow(() ->{
								
								log.warn("Product not found with Id: {}", productId);
								
								return new  ProductNotFoundException(Constants.PRODUCT_NOT_FOUND 
										+ " with Id: " + productId);
							});
		
		log.info("Product fetched successfully with id: {}", product.getProductId());
		
		return ProductMapper.mapToResponse(product);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getAllProducts(int page, int size, 
			String sortBy, SortDirection direction){
		
		
			log.info("Fetching all Products.");
		
		Sort sort = direction == SortDirection.ASC
				? Sort.by(sortBy).ascending()
			    : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size, sort);
		
		log.info("Fetching product with page: {}, size: {}, sortBy : {}, sortDir: {}",
				page, size, sortBy, direction);
		
		Page<Product> products = productRepository.findAll(pageable);
		
		log.info("All products fetched successfully.");
		
		return products.map(ProductMapper::mapToResponse);
	}
	
	@Override
	@Transactional
	@CachePut(value = "products", key = "#productId")
	public ProductResponseDto updateProduct(Long productId, 
			ProductRequestDto requestDto) {
		
		log.info("Updating the product with id: {}", productId);
		
		Product product = productRepository.findById(productId)
							.orElseThrow(() -> {
								log.error("Product with id: {} is not exists", productId);
								
								return new ProductNotFoundException(
									Constants.PRODUCT_NOT_FOUND + " with id "+ productId);
							});
		
		if(!product.getName().equals(requestDto.getName())) {
			
			productRepository.findByName(requestDto.getName()).ifPresent(existsByName -> {
				
				log.warn("Product name already exist {}", requestDto.getName());
				
				throw new DuplicateProductException(Constants.PRODUCT_ALREADY_EXISTS + 
						" with name: "+requestDto.getName());
			});
		}
		
		ProductMapper.updateProduct(product, requestDto);
		
		Product updatedProduct = productRepository.save(product);
		
		log.info("Product updated successfully with id: {}", productId);
		
		return ProductMapper.mapToResponse(updatedProduct);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = "products", key = "#productId")
	public void deleteProduct(Long productId) {
		
		log.info("Deleting the product with id: {}", productId);
		
		Product product = productRepository.findById(productId).orElseThrow(() ->{
		
			log.error("Product not found with id: {}", productId);
			
			return new ProductNotFoundException(Constants.PRODUCT_NOT_FOUND+" with id: "+productId);
		});
		
		productRepository.delete(product);
		
		log.info("The product deleted successfully with id: {}", productId);
	}

	
}


