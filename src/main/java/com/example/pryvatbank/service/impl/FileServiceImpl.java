package com.example.pryvatbank.service.impl;

import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.service.ZipService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements ZipService {

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public List<Card> processZipFileFromURL(String url) throws IOException {
        List<Card> cards = null;
        byte[] zipBytes = restTemplate.getForObject(url, byte[].class);

        File tempZip = File.createTempFile("bininfo.json", "zip");
        Path write = Files.write(tempZip.toPath(), zipBytes);

        try (ZipFile zipFile = ZipFile.builder().setFile(tempZip).get()) {
            Enumeration<ZipArchiveEntry> enumeration = zipFile.getEntries();

            while (enumeration.hasMoreElements()) {
                ZipArchiveEntry entry = enumeration.nextElement();
                if (!entry.isDirectory() && !entry.getName().contains("/")) {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    String content = new String(inputStream.readAllBytes());
                    if (entry.getName().endsWith(".json")) {
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        JsonNode jsonNode = objectMapper.readTree(content);
//                        logger.info(jsonNode.toPrettyString());
                        cards = parseJson(content);
                        logger.info(String.valueOf(cards.get(0).toString()));
                        logger.info(String.valueOf(cards.get(1).toString()));
                        logger.info(String.valueOf(cards.get(584894).toString()));
                    }
                }
            }
        } finally {
            Files.deleteIfExists(write);
        }
        return cards;
    }

    private List<Card> parseJson(String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, new TypeReference<List<Card>>() {});
    }
}
