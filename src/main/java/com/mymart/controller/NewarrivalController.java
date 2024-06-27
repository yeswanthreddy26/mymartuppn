package com.mymart.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

import com.mymart.model.newarrival;
import com.mymart.model.newarrivalDto;
import com.mymart.repository.NewarrivalRepository;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/Admin")

public class NewarrivalController {

	@Autowired

	private NewarrivalRepository repo;

	@GetMapping("/Adminnewarrival")

	public String showcardList(Model model){

	List<newarrival> newarrival = repo.findAll();

		model.addAttribute("newarrival",newarrival);

		return "admin/Adminnewarrival";

	}
	
	@GetMapping("/createnewarrival")

	public String showCreatenewarrival(Model model)

	{

		newarrivalDto newarrivalDto = new newarrivalDto();

		model.addAttribute("newarrivalDto", newarrivalDto);

		return "admin/Createnewarrival";

		

	}

	

	@PostMapping("/createnewarrival")

	public String createnewarrival(

			@Valid @ModelAttribute newarrivalDto newarrivalDto,

			BindingResult result			

			)

	{

		

			if(newarrivalDto.getImageFile().isEmpty())

			{

				result.addError(new FieldError("newarrivalDto","imageFile","The image file is required"));

			}

		

		if(result.hasErrors())

		{

			return "admin/Createnewarrival";

		}

		

		MultipartFile image = newarrivalDto.getImageFile();

		

		

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

		

		newarrival newarrival = new newarrival();

		newarrival.setTitle(newarrivalDto.getTitle());

		

		newarrival.setDescription(newarrivalDto.getDescription());

		

		newarrival.setImageFileName(storageFileName);

		

		repo.save(newarrival);

		

		return "redirect:/Admin/Adminnewarrival";

		

	}
	
	@GetMapping("/editnewarrival")

	public String showEditnewarrival(

			Model model,

			@RequestParam Long id

			) {

		try {

			newarrival newarrival = repo.findById(id).get();

			model.addAttribute("newarrival",newarrival);

			

			newarrivalDto newarrivalDto = new newarrivalDto();

			newarrivalDto.setTitle(newarrival.getTitle());

			
			newarrivalDto.setDescription(newarrival.getDescription());

			

			model.addAttribute("newarrivalDto", newarrivalDto);

			

		}

		catch(Exception e) {

			System.out.println("Exception : " +e.getMessage());

			return "redirect:/Admin/Adminnewarrival";

			

		}

	return "admin/Editnewarrival";	

	}

	

	@PostMapping("/editnewarrival")

	public String updatenewarrival(

			Model model,

			@RequestParam Long id,

			@Valid @ModelAttribute newarrivalDto newarrivalDto,

			BindingResult result

			) {

		try {

			newarrival newarrival = repo.findById(id).get();

			model.addAttribute("newarrival", newarrival);

			

			if(result.hasErrors()) {

				return "admin/Editnewarrival";

			}

			

			if(!newarrivalDto.getImageFile().isEmpty()) {

				//for deleting the old images

				String uploadDir = "public/images/";

				Path oldImagePath = Paths.get(uploadDir  + newarrival.getImageFileName());

				try {

					Files.delete(oldImagePath);

				}

				catch(Exception e) {

					System.out.println("Exception: " +e.getMessage());

				}

				

				//save the new image

				MultipartFile image = newarrivalDto.getImageFile();

				

				String storageFileName = image.getOriginalFilename();

				

				try(InputStream inputStream = image.getInputStream()){

					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),

							StandardCopyOption.REPLACE_EXISTING);

					

				}

				newarrival.setImageFileName(storageFileName);

					

				}

			newarrival.setTitle(newarrivalDto.getTitle());

			
			newarrival.setDescription(newarrivalDto.getDescription());

			

			repo.save(newarrival);

			



		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

			

		}

		

		return "redirect:/Admin/Adminnewarrival";

		

	}

	@GetMapping("/deletenewarrival")

	public String deletenewarrival(

			@RequestParam Long id

			) {

		try {

			newarrival newarrival = repo.findById(id).get();

			

			//for deleting the product image

			Path imagePath = Paths.get("public/images/" + newarrival.getImageFileName());

			

			try {

				Files.delete(imagePath);

				

			}

			catch(Exception e) {

				System.out.println("Exception: " +e.getMessage());

				

			}

			

			//to delete the product

			

			repo.delete(newarrival);

		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

		}

		return "redirect:/Admin/Adminnewarrival";

	}

	
}
