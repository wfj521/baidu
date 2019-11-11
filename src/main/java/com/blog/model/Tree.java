package com.blog.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Tree implements Serializable {
	
	private Integer id;
	
	private String text;
	
	private String href;
	
	private Integer pid;
	
	private List<Tree> nodes;
	
	private Boolean selectable;
	
	private Boolean leaf;

}
