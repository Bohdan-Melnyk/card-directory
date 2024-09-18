package com.example.pryvatbank.service.impl;

import com.example.pryvatbank.dto.CardResponseDto;
import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.entity.CardTemp;
import com.example.pryvatbank.repo.CardRepo;
import com.example.pryvatbank.repo.CardTempRepo;
import com.example.pryvatbank.repo.DataBaseUpdates;
import com.example.pryvatbank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepo cardRepo;

    private final CardTempRepo cardTempRepo;

    private final DataBaseUpdates dataBaseUpdates;

    @Override
    public CardResponseDto getCardInfo(String cardNumber) {
        long parseLong = Long.parseLong(cardNumber.trim() + "000");
        Optional<Card> cardOptional = cardRepo.findByNumberInRange(BigInteger.valueOf(parseLong));
        if (cardOptional.isEmpty()) {
            throw new RuntimeException("Not found");
        }
        Card card = cardOptional.get();
        return new CardResponseDto(
                card.getBin().longValue(),
                card.getAlphaCode(),
                card.getBankName()
        );
    }

    @Override
    @Transactional
    public void saveAll(List<CardTemp> cards) {
        cardTempRepo.saveAll(cards);
    }

    @Override
    @Transactional
    public void switchTables() {
        dataBaseUpdates.renameCardsToCardsOld();

        dataBaseUpdates.renameCardsTempToCards();

        dataBaseUpdates.renameCardsOldToCardsTemp();

        dataBaseUpdates.clearCardsTemp();
    }
}
