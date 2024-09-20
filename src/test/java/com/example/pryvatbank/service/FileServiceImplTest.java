package com.example.pryvatbank.service;

import com.example.pryvatbank.entity.CardTemp;
import com.example.pryvatbank.service.impl.FileServiceImpl;
import com.example.pryvatbank.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FileServiceImpl fileService;

    @TempDir
    private Path path;

    @Test
    void testProcessZipFileFromURLSuccessTest() throws Exception {
        String url = "http://example.com/test.zip";
        byte[] zipBytes = FileUtil.createTestZipFile(path);
        when(restTemplate.getForObject(url, byte[].class)).thenReturn(zipBytes);

        List<CardTemp> cards = fileService.processZipFileFromURL(url);

        assertNotNull(cards);
        assertEquals(5, cards.size());
        assertEquals("Some Bank 0", cards.get(0).getBankName());
        assertEquals("UA0", cards.get(0).getAlphaCode());
        assertEquals(new BigInteger("123450"), cards.get(0).getBin());
        verify(restTemplate).getForObject(url, byte[].class);
    }

    @Test
    void testProcessZipFileFromURLExceptionTest() throws Exception {
        String url = "http://example.com/test.zip";
        byte[] zipBytes = FileUtil.createTestZipFileWithInvalidJson(path);
        when(restTemplate.getForObject(url, byte[].class)).thenReturn(zipBytes);

        FileServiceImpl serviceSpy = spy(fileService);
        doThrow(new JsonProcessingException("Invalid JSON") {}).when(serviceSpy).parseJson(anyString());

        Exception exception = assertThrows(JsonProcessingException.class, () -> serviceSpy.processZipFileFromURL(url));

        assertEquals("Invalid JSON", exception.getMessage());
        verify(restTemplate).getForObject(url, byte[].class);
    }
}
