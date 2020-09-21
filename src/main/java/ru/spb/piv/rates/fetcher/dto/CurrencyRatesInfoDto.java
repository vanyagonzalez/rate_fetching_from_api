package ru.spb.piv.rates.fetcher.dto;

import java.math.BigDecimal;

/**
 * Currency rates information
 */
public class CurrencyRatesInfoDto {

    private String code;
    private BigDecimal currentRate;
    private BigDecimal minRate;
    private BigDecimal maxRate;

    public CurrencyRatesInfoDto(String code, BigDecimal currentRate, BigDecimal minRate, BigDecimal maxRate) {
        this.code = code;
        this.currentRate = currentRate;
        this.minRate = minRate;
        this.maxRate = maxRate;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public BigDecimal getMinRate() {
        return minRate;
    }

    public BigDecimal getMaxRate() {
        return maxRate;
    }

    @Override
    public String toString() {
        return "Rates information {" +
                "code='" + code + '\'' +
                ", currentRate=" + currentRate +
                ", minRate=" + minRate +
                ", maxRate=" + maxRate +
                '}';
    }
}
