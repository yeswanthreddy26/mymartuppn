package com.mymart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mymart.model.card;

public interface CardRepository extends JpaRepository<card, Long>{

}
