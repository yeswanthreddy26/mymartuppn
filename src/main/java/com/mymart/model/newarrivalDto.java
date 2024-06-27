package com.mymart.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class newarrivalDto {

	@NotEmpty(message = "The title is required")
	private String title;


	@Size(min = 10, message = "The description should be atleast 10 characters")
	@Size(max = 2000, message = "The description cannot exceed 2000 characters")
	private String description;

	private MultipartFile imageFile;

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

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

}

