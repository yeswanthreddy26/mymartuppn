package com.mymart.model;

import org.springframework.web.multipart.MultipartFile;

public class KeepintouchDto {

	 private MultipartFile imageFileName;

	    public MultipartFile getImageFileName() {
	        return imageFileName;
	    }

	    public void setImageFileName(MultipartFile imageFileName) {
	        this.imageFileName = imageFileName;
	    }
}
