package com.mymart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mymart.model.Product;
import com.mymart.model.newarrival;
import com.mymart.repository.NewarrivalRepository;
import com.mymart.repository.ProductsRepository;

@Service
public class NewarrivalService {
	
	private final NewarrivalRepository newarrivalRepository;

    @Autowired
    public NewarrivalService(NewarrivalRepository newarrivalRepository) {
        this.newarrivalRepository = newarrivalRepository;
    }
    @Autowired
    public ProductsRepository productRepository;

    public List<newarrival> getAllnewarrivals() {
        return newarrivalRepository.findAll();
    }

    public Optional<newarrival> getnewarrivalById(Long id) {
        return newarrivalRepository.findById(id);
    }

    public newarrival createnewarrival(newarrival newarrival) {
        
        return newarrivalRepository.save(newarrival);
    }
    public void save(newarrival newarrival) {
        newarrivalRepository.save(newarrival);
    }
   
    public void deletenewarrival(Long id) {
        newarrivalRepository.deleteById(id);
    }
    public List<Product> getProductsWithDiscountDeals() {
        return productRepository.findProductsWithDealsDiscount();
    }

	 public newarrival findById(Long id) {
        return newarrivalRepository.findById(id).orElse(null);
    }


}
