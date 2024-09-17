package com.example.pryvatbank.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cards")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Card {

    @Id
    @JsonProperty("bin")
    private Long bin;

    @Column(name = "min_range")
    @JsonProperty("min_range")
    private Double minRange;

    @Column(name = "max_range")
    @JsonProperty("max_range")
    private Double maxRange;

    @Column(name = "alpha_code")
    @JsonProperty("alpha_code")
    private String alphaCode;

    @Column(name = "bank_name")
    @JsonProperty("bank_name")
    private String bankName;
}
