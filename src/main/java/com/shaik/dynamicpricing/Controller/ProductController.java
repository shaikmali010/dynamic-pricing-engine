package com.shaik.dynamicpricing.Controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shaik.dynamicpricing.dto.requestDto.ProductRequestDto;
import com.shaik.dynamicpricing.dto.responseDto.ProductResponseDto;
import com.shaik.dynamicpricing.enums.SortDirection;
import com.shaik.dynamicpricing.service.Inter.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/products")
@Tag(name = "Product APIs",
			description = "Product Management APIs")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping
	@Operation(summary = "Add Product API")
	public ResponseEntity<ProductResponseDto> addProduct(@Valid
			@RequestBody ProductRequestDto requestDto) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				      .body(productService.addProduct(requestDto));
	}
	
	
	@GetMapping
	@Operation(summary = "Fetch all products")
	public ResponseEntity<Page<ProductResponseDto>> getAllProduct(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "productId") String sortBy,
			@RequestParam(defaultValue = "ASC") SortDirection direction){
		
		return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, direction));
	}
	
	@GetMapping("/{productId}")
	@Operation(summary = "Fetch product by id")
	public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId){
		return ResponseEntity.ok(productService.getProductById(productId));
	}
	
	@PutMapping("/{productId}")
	@Operation(summary = "Update the Product")
	public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId, 
			@Valid 
			@RequestBody ProductRequestDto requestDto){
		return ResponseEntity.ok(productService.updateProduct(productId, requestDto));
	}
	
	@DeleteMapping("/{productId}")
	@Operation(summary = "Delete the product")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
		productService.deleteProduct(productId);
		return ResponseEntity.ok("Product deleted successfully.");
	}
	
	
	

}
