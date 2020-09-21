package ru.spb.piv.rates.fetcher.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Historical rates
 */
public class HistoricalRatesDto implements Serializable {
    private Map<String, BigDecimal> bpi = new HashMap<String, BigDecimal>();

    public Map<String, BigDecimal> getBpi() {
        return bpi;
    }
}
