package com.example.pryvatbank.service.impl;

import com.example.pryvatbank.dto.CardResponseDto;
import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.entity.CardTemp;
import com.example.pryvatbank.exception.NotFoundException;
import com.example.pryvatbank.repo.CardRepo;
import com.example.pryvatbank.repo.CardTempRepo;
import com.example.pryvatbank.repo.DataBaseUpdates;
import com.example.pryvatbank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepo cardRepo;

    private final CardTempRepo cardTempRepo;

    private final DataBaseUpdates dataBaseUpdates;

    @Override
    public CardResponseDto getCardInfo(String cardNumber) {
        String fullCardNumber = cardNumber.replaceAll("\\s", "") + "000";
        Optional<Card> cardOptional = cardRepo.findByNumberInRange(BigInteger.valueOf(Long.parseLong(fullCardNumber)));
        if (cardOptional.isEmpty()) {
            throw new NotFoundException("Card with number " + cardNumber + " not found");
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
    public void switchTables(List<CardTemp> cards) {
        cardTempRepo.saveAll(cards);

        dataBaseUpdates.renameCardsToCardsOld();

        dataBaseUpdates.renameCardsTempToCards();

        dataBaseUpdates.renameCardsOldToCardsTemp();

        dataBaseUpdates.clearCardsTemp();
    }
}
