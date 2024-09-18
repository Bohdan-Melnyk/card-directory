package com.example.pryvatbank.repo;

import com.example.pryvatbank.entity.CardTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTempRepo extends JpaRepository<CardTemp, Long> {
}
