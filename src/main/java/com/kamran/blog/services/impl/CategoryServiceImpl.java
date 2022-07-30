package com.kamran.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kamran.blog.entities.Category;
import com.kamran.blog.exceptions.ResourceNotFoundException;
import com.kamran.blog.payloads.CategoryDto;
import com.kamran.blog.repositories.CategoryRepo;
import com.kamran.blog.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = this.dtoToCategory(categoryDto);
		Category savedCategory= this.categoryRepo.save(category);
		return this.categoryToDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId) );
	
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(category);
		
		return this.categoryToDto(updatedCategory);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId) );
		return this.categoryToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCategories = this.categoryRepo.findAll();
		List<CategoryDto> allCategoryDtos =  allCategories.stream().map(category->this.categoryToDto(category)).collect(Collectors.toList());
		
		return allCategoryDtos;
	}

	@Override
	public void deleteCategoryr(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId) );
		
		this.categoryRepo.delete(category);
	}
	
	
	public Category dtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
		
	}
	
	public CategoryDto categoryToDto(Category user) {
		CategoryDto categoryDto = this.modelMapper.map(user, CategoryDto.class);
		return categoryDto;
	}

}
