package com.kamran.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamran.blog.entities.Comment;
import com.kamran.blog.entities.Post;
import com.kamran.blog.entities.User;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	
	List<Comment> getAllByUser(User user);
	
	List<Comment> getAllByPost(Post post);

}
