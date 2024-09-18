package com.example.pryvatbank.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "cards_temp")
@Getter
@Setter
@ToString
public class CardTemp {
    @Id
    @JsonProperty("bin")
    private BigInteger bin;

    @Column(name = "min_range")
    @JsonProperty("min_range")
    private BigInteger minRange;

    @Column(name = "max_range")
    @JsonProperty("max_range")
    private BigInteger maxRange;

    @Column(name = "alpha_code")
    @JsonProperty("alpha_code")
    private String alphaCode;

    @Column(name = "bank_name")
    @JsonProperty("bank_name")
    private String bankName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CardTemp cardTemp = (CardTemp) o;
        return bin != null && Objects.equals(bin, cardTemp.bin);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
