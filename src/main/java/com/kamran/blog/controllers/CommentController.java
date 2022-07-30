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
import org.springframework.web.bind.annotation.RestController;

import com.kamran.blog.payloads.ApiResponse;
import com.kamran.blog.payloads.CommentDto;
import com.kamran.blog.services.CommentService;
@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable("userId") Integer uid,
			@PathVariable("postId") Integer cid
			){
		CommentDto createdComment= this.commentService.addComment(commentDto, uid, cid);
		return new ResponseEntity<CommentDto>( createdComment, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable("commentId") Integer commentId){
		CommentDto updatedComment= this.commentService.updateComment(commentDto, commentId);
		return new ResponseEntity<CommentDto>(updatedComment, HttpStatus.OK);
	}	

	
	@GetMapping("/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId") Integer commentId){
		CommentDto comment= this.commentService.getCommentById(commentId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
	}	

	

	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
	}	
	
	
	@GetMapping("/comments/post/{postId}")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") Integer postId){
		List<CommentDto> allCommentsForPost = this.commentService.getAllCommentsForPost(postId);
		return new ResponseEntity<List<CommentDto>>(allCommentsForPost, HttpStatus.OK);
	}
	
	@GetMapping("/comments/user/{userId}")
	public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable("userId") Integer userId){
		List<CommentDto> allCommentsOfUser= this.commentService.getAllCommentsForUser(userId);
		return new ResponseEntity<List<CommentDto>>(allCommentsOfUser, HttpStatus.OK);
	}	
	
	
	
}












