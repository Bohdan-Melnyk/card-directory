package com.example.pryvatbank.service;

import com.example.pryvatbank.dto.CardResponseDto;
import com.example.pryvatbank.entity.Card;

import java.util.List;

public interface CardService {

    CardResponseDto getCardInfo(String cardNumber);

    void saveAll(List<Card> cards);

    void switchTables();
}
