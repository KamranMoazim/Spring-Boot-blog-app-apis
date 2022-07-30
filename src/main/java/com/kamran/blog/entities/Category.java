package com.kamran.blog.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="title", length = 100, nullable = false)
	private String categoryTitle;
	
	@Column(name="description")
	private String categoryDescription;
	
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 'category' ---> used in Post.java Entity
	private List<Post> posts = new ArrayList<>();

}
