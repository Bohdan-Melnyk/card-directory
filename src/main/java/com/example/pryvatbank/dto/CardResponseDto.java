package com.example.pryvatbank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardResponseDto(
        @JsonProperty long bin,
        @JsonProperty String alphaCode,
        @JsonProperty String bankName
) {
}
