package com.example.pryvatbank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardNumberRequest(@JsonProperty String cardNumber) {
}
