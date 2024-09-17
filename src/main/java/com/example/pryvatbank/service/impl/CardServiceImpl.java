package com.example.pryvatbank.service.impl;

import com.example.pryvatbank.dto.CardResponseDto;
import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.repo.CardRepo;
import com.example.pryvatbank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepo cardRepo;

    @Override
    public CardResponseDto getCardInfo(String cardNumber) {
        Optional<Card> cardOptional = cardRepo.findByNumberInRange(Long.parseLong(cardNumber + "000"));
        if (cardOptional.isEmpty()) {
            throw new RuntimeException("Not found");
        }
        Card card = cardOptional.get();
        return new CardResponseDto(
                card.getBin(),
                card.getAlphaCode(),
                card.getBankName()
        );
    }

    @Override
    public void saveAll(List<Card> cards) {
        cardRepo.saveAll(cards);
    }

    @Override
    public void switchTables() {

    }
}
