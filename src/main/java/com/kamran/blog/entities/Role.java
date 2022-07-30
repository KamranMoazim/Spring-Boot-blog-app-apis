package com.kamran.blog.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;

}
