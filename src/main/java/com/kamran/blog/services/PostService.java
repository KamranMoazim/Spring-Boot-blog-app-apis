package com.kamran.blog.services;

import java.util.List;

import com.kamran.blog.payloads.PostDto;
import com.kamran.blog.payloads.PostReponse;



public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	PostDto getPostById(Integer postId);
	void deletePost(Integer postId);
//	List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize);
	PostReponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	List<PostDto> getPostsByUser(Integer userId);
//	List<PostDto> searchPosts(String keyword);
}
