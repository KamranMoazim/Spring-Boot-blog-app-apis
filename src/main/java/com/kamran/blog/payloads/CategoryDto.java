package com.kamran.blog.payloads;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=5, message="Category Title must be at least 5 characters!")
	private String categoryTitle;
	
	@NotEmpty
	private String categoryDescription;

}
