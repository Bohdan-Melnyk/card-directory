package com.example.pryvatbank.repo;

import com.example.pryvatbank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {

    @Query(value = "select * from cards where :number between min_range and max_range", nativeQuery = true)
    Optional<Card> findByNumberInRange(@Param(value = "number") BigInteger cardNumber);

    @Query(value = "select count(*) from cards", nativeQuery = true)
    int countAllAmount();
}
