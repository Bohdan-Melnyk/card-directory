package com.example.pryvatbank.repo.impl;

import com.example.pryvatbank.repo.DataBaseUpdates;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DataBaseUpdatesImpl implements DataBaseUpdates {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void renameCardsToCardsOld() {
        entityManager.createNativeQuery("alter table cards rename to cards_old").executeUpdate();
    }

    @Override
    public void renameCardsTempToCards() {
        entityManager.createNativeQuery("alter table cards_temp rename to cards").executeUpdate();
    }

    @Override
    public void renameCardsOldToCardsTemp() {
        entityManager.createNativeQuery("alter table cards_old rename to cards_temp").executeUpdate();
    }

    @Override
    public void clearCardsTemp() {
        entityManager.createNativeQuery("truncate table cards_temp").executeUpdate();
    }
}
