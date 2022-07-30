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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kamran.blog.config.AppConstants;
import com.kamran.blog.payloads.ApiResponse;
import com.kamran.blog.payloads.PostDto;
import com.kamran.blog.payloads.PostReponse;
import com.kamran.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable("userId") Integer uid,
			@PathVariable("categoryId") Integer cid
			){
		PostDto createdPost= this.postService.createPost(postDto, uid, cid);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId){
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}	
	
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
	}	
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
		PostDto post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}	
	
//	@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//	@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
//	@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
//	@RequestParam(value = "sortDir", defaultValue = "as", required = false) String sortDir,
	
	@GetMapping("/posts")
	public ResponseEntity<PostReponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
//		List<PostDto> allPosts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		PostReponse allPosts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostReponse>(allPosts, HttpStatus.OK);
	}
	
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable("categoryId") Integer cid){
		List<PostDto> allPosts = this.postService.getPostsByCategory(cid);
		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPost(@PathVariable("userId") Integer uid){
		List<PostDto> allPosts = this.postService.getPostsByUser(uid);
		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
	}

//	@GetMapping("/posts/search/{keywords}")
//	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
//		List<PostDto> allPosts = this.postService.searchPosts(keywords);
//		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
//	}

}







