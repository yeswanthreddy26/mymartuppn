package com.mymart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table(name="card")
public class card {

	@Id

	@GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;

    private String title;

    private String description;

    private String imageFileName;

    private String category;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public card(Long id, String title, String description, String imageFileName) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageFileName = imageFileName;
		
	}

	public card(String category) {
		super();
		this.category = category;
	}

	public card() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "card [id=" + id + ", title=" + title + ", description=" + description + ", imageFileName="
				+ imageFileName + " , category=" + category + "]";
	}

 
}
