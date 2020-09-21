package ru.spb.piv.rates.fetcher.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Current prices
 */
public class CurrentPriceDto implements Serializable {
    private Map<String, CurrencyRateDto> bpi;

    public Map<String, CurrencyRateDto> getBpi() {
        return bpi;
    }
}
