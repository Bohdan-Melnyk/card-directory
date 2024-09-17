package com.example.pryvatbank.scheduler;

import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.service.CardService;
import com.example.pryvatbank.service.ZipService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private final RestTemplate restTemplate;

    private final ZipService zipService;

    private final CardService cardService;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void testCall() throws IOException {
        logger.info("Request start");
        List<Card> cards = zipService.processZipFileFromURL("https://ecom-bininfo.s3.eu-west-1.amazonaws.com/bininfo.json.zip");
        logger.info("Request ends");
        cardService.saveAll(cards);
    }
}
