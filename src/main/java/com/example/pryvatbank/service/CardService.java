package com.example.pryvatbank.service;

import com.example.pryvatbank.dto.CardResponseDto;
import com.example.pryvatbank.entity.CardTemp;

import java.util.List;

public interface CardService {

    CardResponseDto getCardInfo(String cardNumber);

    void switchTables(List<CardTemp> cards);
}
