package com.mymart.model;



import org.springframework.web.multipart.MultipartFile;



public class carouselDto {

	

	public MultipartFile getImageFile() {

		return imageFile;

	}



	public void setImageFile(MultipartFile imageFile) {

		this.imageFile = imageFile;

	}



	private MultipartFile imageFile;



	



}

