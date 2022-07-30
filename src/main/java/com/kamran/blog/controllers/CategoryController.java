package com.kamran.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamran.blog.payloads.ApiResponse;
import com.kamran.blog.payloads.CategoryDto;
import com.kamran.blog.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto createdCategoryDto = this.categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategoryDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer categoryId){
		this.categoryService.deleteCategoryr(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully!", true), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId){
		CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
		return ResponseEntity.ok(categoryById);
	}
	

}

















