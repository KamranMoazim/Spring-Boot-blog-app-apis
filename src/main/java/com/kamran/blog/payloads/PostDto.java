package com.kamran.blog.payloads;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	private int id;
	
	private String title;
	private String content;
	private String imageName;
	
	private Date addedDate;
	private Date updatedDate;
	
	private CategoryDto category;
	private UserDto user;

}
