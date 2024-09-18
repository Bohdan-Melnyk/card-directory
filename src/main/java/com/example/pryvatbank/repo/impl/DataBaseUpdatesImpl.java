package com.example.pryvatbank.repo.impl;

import com.example.pryvatbank.repo.DataBaseUpdates;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DataBaseUpdatesImpl implements DataBaseUpdates {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void renameCardsToCardsOld() {
        entityManager.createNativeQuery("alter table cards rename to cards_old").executeUpdate();
    }

    @Override
    @Transactional
    public void renameCardsTempToCards() {
        entityManager.createNativeQuery("alter table cards_temp rename to cards").executeUpdate();
    }

    @Override
    @Transactional
    public void renameCardsOldToCardsTemp() {
        entityManager.createNativeQuery("alter table cards_old rename to cards_temp").executeUpdate();
    }

    @Override
    @Transactional
    public void clearCardsTemp() {
        entityManager.createNativeQuery("truncate table cards_temp").executeUpdate();
    }
}
