package com.example.pryvatbank.service;

import com.example.pryvatbank.dto.CardResponseDto;
import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.entity.CardTemp;
import com.example.pryvatbank.exception.NotFoundException;
import com.example.pryvatbank.repo.CardRepo;
import com.example.pryvatbank.repo.CardTempRepo;
import com.example.pryvatbank.repo.impl.DataBaseUpdatesImpl;
import com.example.pryvatbank.service.impl.CardServiceImpl;
import com.example.pryvatbank.util.CardCommonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CardServiceImplTest {

    @Mock
    private CardRepo cardRepo;

    @Mock
    private CardTempRepo cardTempRepo;

    @Mock
    private DataBaseUpdatesImpl dataBaseUpdates;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void getCardInfoSuccessTest() {
        String cardNumber = "12345";
        String fullCardNumber = cardNumber.replaceAll("\\s", "") + "000";
        Optional<Card> optionalCard = Optional.of(CardCommonUtil.createSingleCard());

        when(cardRepo.findByNumberInRange(BigInteger.valueOf(Long.parseLong(fullCardNumber))))
                .thenReturn(optionalCard);

        CardResponseDto cardInfo = cardService.getCardInfo(cardNumber);

        assertEquals(12345000, cardInfo.bin());
        assertEquals("USD", cardInfo.alphaCode());
        assertEquals("Test Bank", cardInfo.bankName());
        verify(cardRepo, times(1)).findByNumberInRange(BigInteger.valueOf(Long.parseLong(fullCardNumber)));

    }

    @Test
    void getCardInfoExceptionTest() {
        String cardNumber = "12345";
        String fullCardNumber = cardNumber.replaceAll("\\s", "") + "000";

        when(cardRepo.findByNumberInRange(BigInteger.valueOf(Long.parseLong(fullCardNumber))))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> cardService.getCardInfo(cardNumber));
        assertEquals("Card with number " + cardNumber + " not found", exception.getMessage());
        verify(cardRepo, times(1)).findByNumberInRange(BigInteger.valueOf(Long.parseLong(fullCardNumber)));

    }

    @Test
    void testSwitchTablesTest() {
        List<CardTemp> cardTemps = CardCommonUtil.createCardTempList();

        when(cardTempRepo.saveAll(cardTemps)).thenReturn(cardTemps);
        doNothing().when(dataBaseUpdates).renameCardsToCardsOld();
        doNothing().when(dataBaseUpdates).renameCardsTempToCards();
        doNothing().when(dataBaseUpdates).renameCardsOldToCardsTemp();
        doNothing().when(dataBaseUpdates).clearCardsTemp();

        cardService.switchTables(cardTemps);

        verify(cardTempRepo, times(1)).saveAll(cardTemps);
        verify(dataBaseUpdates, times(1)).renameCardsToCardsOld();
        verify(dataBaseUpdates, times(1)).renameCardsTempToCards();
        verify(dataBaseUpdates, times(1)).renameCardsOldToCardsTemp();
        verify(dataBaseUpdates, times(1)).clearCardsTemp();
    }
}
