package com.kamran.blog.entities;

//import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false)
	private String content;

	@ManyToOne
	private Post post;
	
	@ManyToOne
	private User user;

}
