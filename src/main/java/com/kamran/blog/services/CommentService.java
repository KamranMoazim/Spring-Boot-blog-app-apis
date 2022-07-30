package com.kamran.blog.services;

import java.util.List;

import com.kamran.blog.payloads.CommentDto;


public interface CommentService {
	
	CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId);
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
	CommentDto getCommentById(Integer commentId);
	List<CommentDto> getAllCommentsForPost(Integer postId);
	List<CommentDto> getAllCommentsForUser(Integer userId);
	void deleteComment(Integer commentId);
	
}
