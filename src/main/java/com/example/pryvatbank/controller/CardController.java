package com.example.pryvatbank.controller;

import com.example.pryvatbank.dto.CardNumberRequest;
import com.example.pryvatbank.dto.CardResponseDto;
import com.example.pryvatbank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CardController {

    private final Logger logger = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;

    @PostMapping("/card")
    public ResponseEntity<CardResponseDto> fetchData(@RequestBody CardNumberRequest cardNumberRequest) {
        String cardNumber = cardNumberRequest.cardNumber();
        logger.info(cardNumber);
        return ResponseEntity.ok().body(cardService.getCardInfo(cardNumber));
    }
}
