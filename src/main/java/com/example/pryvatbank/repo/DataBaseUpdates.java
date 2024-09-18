package com.example.pryvatbank.repo;

public interface DataBaseUpdates {

    void renameCardsToCardsOld();

    void renameCardsTempToCards();

    void renameCardsOldToCardsTemp();

    void clearCardsTemp();
}
