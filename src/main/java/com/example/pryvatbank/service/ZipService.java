package com.example.pryvatbank.service;

import com.example.pryvatbank.entity.Card;

import java.io.IOException;
import java.util.List;

public interface ZipService {
    List<Card> processZipFileFromURL(String url) throws IOException;
}
