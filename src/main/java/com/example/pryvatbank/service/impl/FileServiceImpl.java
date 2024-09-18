package com.example.pryvatbank.service.impl;

import com.example.pryvatbank.entity.CardTemp;
import com.example.pryvatbank.service.ZipService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
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

    @Override
    public List<CardTemp> processZipFileFromURL(String url) throws IOException {
        List<CardTemp> cards = null;
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
                        cards = parseJson(content);
                    }
                }
            }
        } catch (Exception e) {
            Files.deleteIfExists(write);
        } finally {
            Files.deleteIfExists(write);
        }
        return cards;
    }

    private List<CardTemp> parseJson(String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, new TypeReference<List<CardTemp>>() {});
    }
}
