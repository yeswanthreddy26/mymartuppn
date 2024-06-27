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

import com.mymart.model.card;
import com.mymart.model.cardDto;
import com.mymart.repository.CardRepository;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/Admin")

public class CardController {

	
	@Autowired

	private CardRepository repo;

	@GetMapping("/Admincard")

	public String showcardList(Model model){

	List<card> card = repo.findAll();

		model.addAttribute("card",card);

		return "admin/Admincard";

	}
	
	
	@GetMapping("/createcard")

	public String showCreatecard(Model model)

	{

		cardDto cardDto = new cardDto();

		model.addAttribute("cardDto", cardDto);

		return "admin/Createcard";

		

	}

	

	@PostMapping("/createcard")

	public String createcard(

			@Valid @ModelAttribute cardDto cardDto,

			BindingResult result			

			)

	{

		

			if(cardDto.getImageFile().isEmpty())

			{

				result.addError(new FieldError("cardDto","imageFile","The image file is required"));

			}

		

		if(result.hasErrors())

		{

			return "admin/Createcard";

		}

		

		MultipartFile image = cardDto.getImageFile();

		

		

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

		

		card card = new card();

		card.setTitle(cardDto.getTitle());

		

		card.setDescription(cardDto.getDescription());

		

		card.setImageFileName(storageFileName);

		card.setCategory(cardDto.getCategory());

		repo.save(card);

		

		return "redirect:/Admin/Admincard";

		

	}
	
	@GetMapping("/editcard")

	public String showEditcard(

			Model model,

			@RequestParam Long id

			) {

		try {

			card card = repo.findById(id).get();

			model.addAttribute("card",card);

			

			cardDto cardDto = new cardDto();

			cardDto.setTitle(card.getTitle());

			
			cardDto.setDescription(card.getDescription());
			
			cardDto.setCategory(card.getCategory());

			

			model.addAttribute("cardDto", cardDto);

			

		}

		catch(Exception e) {

			System.out.println("Exception : " +e.getMessage());

			return "redirect:/Admin/Admincard";

			

		}

	return "admin/Editcard";	

	}

	

	@PostMapping("/editcard")

	public String updatecard(

			Model model,

			@RequestParam Long id,

			@Valid @ModelAttribute cardDto cardDto,

			BindingResult result

			) {

		try {

			card card = repo.findById(id).get();

			model.addAttribute("card", card);

			

			if(result.hasErrors()) {

				return "admin/Editcard";

			}

			

			if(!cardDto.getImageFile().isEmpty()) {

				//for deleting the old images

				String uploadDir = "public/images/";

				Path oldImagePath = Paths.get(uploadDir  + card.getImageFileName());

				try {

					Files.delete(oldImagePath);

				}

				catch(Exception e) {

					System.out.println("Exception: " +e.getMessage());

				}

				

				//save the new image

				MultipartFile image = cardDto.getImageFile();

				

				String storageFileName = image.getOriginalFilename();

				

				try(InputStream inputStream = image.getInputStream()){

					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),

							StandardCopyOption.REPLACE_EXISTING);

					

				}

				card.setImageFileName(storageFileName);

					

				}

			card.setTitle(cardDto.getTitle());

			
			card.setDescription(cardDto.getDescription());

			card.setCategory(cardDto.getCategory());

			repo.save(card);

			



		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

			

		}

		

		return "redirect:/Admin/Admincard";

		

	}

	@GetMapping("/deletecard")

	public String deletecard(

			@RequestParam Long id

			) {

		try {

			card card = repo.findById(id).get();

			

			//for deleting the product image

			Path imagePath = Paths.get("public/images/" + card.getImageFileName());

			

			try {

				Files.delete(imagePath);

				

			}

			catch(Exception e) {

				System.out.println("Exception: " +e.getMessage());

				

			}

			

			//to delete the product

			

			repo.delete(card);

		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

		}

		return "redirect:/Admin/Admincard";

	}
	
	

}


