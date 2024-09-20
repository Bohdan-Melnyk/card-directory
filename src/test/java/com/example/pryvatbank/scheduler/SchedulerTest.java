package com.example.pryvatbank.scheduler;

import com.example.pryvatbank.entity.CardTemp;
import com.example.pryvatbank.service.CardService;
import com.example.pryvatbank.service.ZipService;
import com.example.pryvatbank.util.CardCommonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SchedulerTest {

    @Mock
    private ZipService zipService;

    @Mock
    private CardService cardService;

    @InjectMocks
    private Scheduler scheduler;

    @Test
    void fetchDataTestSuccess() throws IOException {
        List<CardTemp> cardTempList = CardCommonUtil.createCardTempList();

        when(zipService.processZipFileFromURL(anyString())).thenReturn(cardTempList);
        doNothing().when(cardService).switchTables(cardTempList);

        scheduler.fetchData();

        verify(zipService, times(1)).processZipFileFromURL(anyString());
        verify(cardService, times(1)).switchTables(cardTempList);
    }
}
