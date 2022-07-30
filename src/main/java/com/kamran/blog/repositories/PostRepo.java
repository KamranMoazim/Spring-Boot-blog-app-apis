package com.kamran.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kamran.blog.entities.Category;
import com.kamran.blog.entities.Post;
import com.kamran.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> getAllByUser(User user);
	
	List<Post> getAllByCategory(Category category);
	
//	@Query("select p form Post p where p.title like :key")
//	List<Post> searchByTitle(@Param("key") String keyword);
	
//	List<Post> findByTitleContaining(String keyword);
	
//	List<Post> findBy_FIELD_NAME_HERE_Containing(Category category);

}
