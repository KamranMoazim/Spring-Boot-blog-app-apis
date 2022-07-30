package com.kamran.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kamran.blog.entities.Comment;
import com.kamran.blog.entities.Post;
import com.kamran.blog.entities.User;
import com.kamran.blog.exceptions.ResourceNotFoundException;
import com.kamran.blog.payloads.CommentDto;
import com.kamran.blog.repositories.CommentRepo;
import com.kamran.blog.repositories.PostRepo;
import com.kamran.blog.repositories.UserRepo;
import com.kamran.blog.services.CommentService;



@Service
public class CommentServiceImpl implements CommentService {
	
	

	
	
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CommentDto addComment(CommentDto commentDto,  Integer userId, Integer postId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setUser(user);
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
//		this.commentRepo.
		return this.modelMapper.map(savedComment, CommentDto.class);
	}
	
	
	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		
		comment.setContent(commentDto.getContent());
		
		Comment updatedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(updatedComment , CommentDto.class);
	}
	
	
	@Override
	public CommentDto getCommentById(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		
		return this.modelMapper.map(comment , CommentDto.class);
	}
	
	
	@Override
	public List<CommentDto> getAllCommentsForPost(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		
		List<Comment> allByPost = this.commentRepo.getAllByPost(post);
		
		return allByPost.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public List<CommentDto> getAllCommentsForUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		List<Comment> allByUser= this.commentRepo.getAllByUser(user);
		
//		System.out.println(allByUser);
//		log.info(allByUser);
		
		return allByUser.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}
	
	
	
	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		
		this.commentRepo.delete(comment);
		
	}

}
