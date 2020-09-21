package ru.spb.piv.rates.fetcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Dto for Current Rate
 */
public class CurrencyRateDto implements Serializable {

    private String code;
    @JsonProperty("rate_float")
    private BigDecimal rate;

    public CurrencyRateDto() {
    }

    public CurrencyRateDto(String code, BigDecimal rate) {
        this.code = code;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
