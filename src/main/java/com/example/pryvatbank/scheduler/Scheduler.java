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

    private static final String URL = "https://ecom-bininfo.s3.eu-west-1.amazonaws.com/bininfo.json.zip";

    private final CardService cardService;

    @Scheduled(fixedRate = 3600000)
    public void fetchData() throws IOException {
        List<CardTemp> cardTemps = zipService.processZipFileFromURL(URL);
        cardService.switchTables(cardTemps);
    }
}
