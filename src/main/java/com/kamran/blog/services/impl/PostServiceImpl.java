package com.kamran.blog.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kamran.blog.entities.Category;
import com.kamran.blog.entities.Post;
import com.kamran.blog.entities.User;
import com.kamran.blog.exceptions.ResourceNotFoundException;
import com.kamran.blog.payloads.PostDto;
import com.kamran.blog.payloads.PostReponse;
import com.kamran.blog.repositories.CategoryRepo;
import com.kamran.blog.repositories.PostRepo;
import com.kamran.blog.repositories.UserRepo;
import com.kamran.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId) );
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("defaul.png");
		
		post.setUser(user);
		post.setCategory(category);
		
		post.setAddedDate(new Date());
		post.setUpdatedDate(new Date());
		
//		this.postRepo.save(post);
		
		Post newPost = this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}


	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		
		Post comingPost= this.modelMapper.map(postDto, Post.class);
		
		post.setUpdatedDate(new Date());
		post.setContent(comingPost.getContent());
		post.setTitle(comingPost.getTitle());
		post.setImageName(comingPost.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}


	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}


	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		this.postRepo.delete(post);
	}


	@Override
	public PostReponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		 Pageable p =  PageRequest.of(pageNumber, pageSize, sort);
		 Page<Post> allPagePosts = this.postRepo.findAll(p);
		 List<Post> allPosts = allPagePosts.getContent();
//		List<Post> allPosts = this.postRepo.findAll();
		List<PostDto> allPostsDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostReponse postReponse = new PostReponse();
		postReponse.setContent(allPostsDtos);
		postReponse.setPageNumber(allPagePosts.getNumber());
		postReponse.setPageSize(allPagePosts.getSize());
		postReponse.setTotalElements(allPagePosts.getTotalElements());
		postReponse.setTotalPages(allPagePosts.getTotalPages());
		postReponse.setLastPage(allPagePosts.isLast());
		
//		return allPostsDtos;
		return postReponse;
	}


	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId) );
		List<Post> allPosts = this.postRepo.getAllByCategory(category);
		List<PostDto> allPostsDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return allPostsDtos;
	}


	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		List<Post> allPosts = this.postRepo.getAllByUser(user);
		
		List<PostDto> allPostsDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return allPostsDtos;
	}


//	@Override
//	public List<PostDto> searchPosts(String keyword) {
//		List<Post> findByTitleContainingPosts = this.postRepo.searchByTitle(keyword);
//		List<PostDto> posts = findByTitleContainingPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		return posts;
//	}

}
