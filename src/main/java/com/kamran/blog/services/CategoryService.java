package com.kamran.blog.services;

import java.util.List;

import com.kamran.blog.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto, Integer userId);
	CategoryDto getCategoryById(Integer categoryDto);
	List<CategoryDto> getAllCategories();
	void deleteCategoryr(Integer userId);

}
