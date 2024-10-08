package com.example.pryvatbank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "cards")
@Getter
@Setter
@ToString
public class Card {

    @Id
    private BigInteger bin;

    @Column(name = "min_range")
    private BigInteger minRange;

    @Column(name = "max_range")
    private BigInteger maxRange;

    @Column(name = "alpha_code")
    private String alphaCode;

    @Column(name = "bank_name")
    private String bankName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Card card = (Card) o;
        return bin != null && Objects.equals(bin, card.bin);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
