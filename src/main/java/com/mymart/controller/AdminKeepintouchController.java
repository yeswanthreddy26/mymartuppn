package com.mymart.controller;



import java.io.InputStream;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;



import com.mymart.model.Keepintouch;

import com.mymart.model.KeepintouchDto;

import com.mymart.repository.KeepintouchRepository;

import jakarta.validation.Valid;



@Controller



@RequestMapping("/Admin")

public class AdminKeepintouchController 

{



	@Autowired

	private KeepintouchRepository repo;

	

	

	@GetMapping("/createkeepintouch")



	public String showCreatekeepintouchPage(Model model)



	{



		KeepintouchDto keepintouchDto = new KeepintouchDto(); 



		model.addAttribute("keepintouchDto", keepintouchDto);



		return "admin/createkeepintouch";



		



	}



	@PostMapping("/createkeepintouch")



	public String createkeepintouch(



			@Valid @ModelAttribute KeepintouchDto keepintouchDto,



			BindingResult result			



			)



	{



		try {



			if(keepintouchDto.getImageFileName().isEmpty())



			{



				result.addError(new FieldError("keepintouchDto","imageFile","The image file is required"));



			}



		} catch (Exception e) {



		



			e.printStackTrace();



		}



		



		if(result.hasErrors())



		{



			return "admin/createkeepintouch";



		}



		



		MultipartFile image = keepintouchDto.getImageFileName();



		String storageFileName = image.getOriginalFilename();



		



		try



	{



			String uploadDir = "public/images/";



			Path uploadPath = Paths.get(uploadDir);



			



			if(!Files.exists(uploadPath))



			{



				Files.createDirectories(uploadPath);



				



			}



			



		try(InputStream inputStream = image.getInputStream())



		{



			Files.copy(inputStream, Paths.get(uploadDir + storageFileName),



					StandardCopyOption.REPLACE_EXISTING);



		}			



	}catch(Exception ex)



		{



		



			System.out.println("Exception : " +ex.getMessage());



			



		}



		



		Keepintouch keepintouch = new Keepintouch();



		



		keepintouch.setImageFileName(storageFileName);



		



		repo.save(keepintouch);



		



		return "redirect:/Admin/Adminfooter";



		



	}

	

	@GetMapping("/editkeepintouch")

	public String showEditkeepintouchPage(

			Model model,

			@RequestParam long id

			) {

		try {

			Keepintouch keepintouch = repo.findById(id).get();

			model.addAttribute("keepintouch",keepintouch);

			

			KeepintouchDto keepintouchDto = new KeepintouchDto();

			

			

			model.addAttribute("keepintouchDto", keepintouchDto);

			

		}

		catch(Exception e) {

			System.out.println("Exception : " +e.getMessage());

			return "redirect:/Admin/Adminfooter";

			

		}

	return "admin/editkeepintouch";	

	}

	

	@PostMapping("/editkeepintouch")

	public String updateproduct(

			Model model,

			@RequestParam long id,

			@Valid @ModelAttribute KeepintouchDto keepintouchDto,

			BindingResult result

			) {

		try {

			Keepintouch keepintouch = repo.findById(id).get();

			model.addAttribute("keepintouch", keepintouch);

			

			if(result.hasErrors()) {

				return "admin/editkeepintouch";

			}

			

			if(!keepintouchDto.getImageFileName().isEmpty()) {

				//for deleting the old images

				String uploadDir = "public/images/";

				Path oldImagePath = Paths.get(uploadDir  + keepintouch.getImageFileName());

				try {

					Files.delete(oldImagePath);

				}

				catch(Exception e) {

					System.out.println("Exception: " +e.getMessage());

				}

				

				//save the new image

				MultipartFile image = keepintouchDto.getImageFileName();

				

				String storageFileName = image.getOriginalFilename();

				

				try(InputStream inputStream = image.getInputStream()){

					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),

							StandardCopyOption.REPLACE_EXISTING);

					

				}

				keepintouch.setImageFileName(storageFileName);

					

				}

			

			repo.save(keepintouch);

			



		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

			

		}

		

		return "redirect:/Admin/Adminfooter";

		

	}

	

	@GetMapping("/deletekeepintouch")

	public String deleteProduct(

			@RequestParam long id

			) {

		try {

			Keepintouch keepintouch = repo.findById(id).get();

			

			//for deleting the product image

			Path imagePath = Paths.get("public/images/" + keepintouch.getImageFileName());

			

			try {

				Files.delete(imagePath);

				

			}

			catch(Exception e) {

				System.out.println("Exception: " +e.getMessage());

				

			}

			

			//to delete the product

			

			repo.delete(keepintouch);

		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

		}

		return "redirect:/Admin/Adminfooter";

	}

	

	

}