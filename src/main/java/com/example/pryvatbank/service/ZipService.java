package com.example.pryvatbank.service;

import com.example.pryvatbank.entity.Card;
import com.example.pryvatbank.entity.CardTemp;

import java.io.IOException;
import java.util.List;

public interface ZipService {

    List<CardTemp> processZipFileFromURL(String url) throws IOException;
}
