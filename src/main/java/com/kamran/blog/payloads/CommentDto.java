package com.kamran.blog.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
	private int id;
	
	private String content;
	
	private PostDto post;
	private UserDto user;

}
