package com.example.pryvatbank.scheduler;

import com.example.pryvatbank.entity.CardTemp;
import com.example.pryvatbank.service.CardService;
import com.example.pryvatbank.service.ZipService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ZipService zipService;

    private final CardService cardService;

    @Scheduled(fixedRate = 3600000)
    public void testCall() throws IOException {
        List<CardTemp> cardTemps = zipService.processZipFileFromURL("https://ecom-bininfo.s3.eu-west-1.amazonaws.com/bininfo.json.zip");
        cardService.saveAll(cardTemps);
        cardService.switchTables();
    }
}
