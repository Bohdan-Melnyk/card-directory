package com.example.pryvatbank.util;

import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.entity.CardTemp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CardCommonUtil {

    public static Card createSingleCard() {
        Card card = new Card();
        card.setBin(new BigInteger("12345000"));
        card.setMinRange(new BigInteger("100000"));
        card.setMaxRange(new BigInteger("999999"));
        card.setAlphaCode("USD");
        card.setBankName("Test Bank");
        return card;
    }

    public static List<CardTemp> createCardTempList() {
        List<CardTemp> cardTempList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CardTemp cardTemp = new CardTemp();
            cardTemp.setBin(new BigInteger("12345" + i));
            cardTemp.setMinRange(new BigInteger("10000" + i));
            cardTemp.setMaxRange(new BigInteger("99999" + i));
            cardTemp.setAlphaCode("UA" + i);
            cardTemp.setBankName("Some Bank " + i);
            cardTempList.add(cardTemp);
        }
        return cardTempList;
    }

    public static String convertCardTempListToJson() {
        List<CardTemp> cardTempList = createCardTempList();
        try {
            return new ObjectMapper().writeValueAsString(cardTempList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
