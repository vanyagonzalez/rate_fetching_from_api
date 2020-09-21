package ru.spb.piv.rates.fetcher.service;

import ru.spb.piv.rates.fetcher.dto.CurrencyRatesInfoDto;

/**
 * Service for calculation rates information
 */
public interface RateInfoPreparerService {
    /**
     * Prepare rates info
     * @param currency input currency
     * @return rate information
     */
    CurrencyRatesInfoDto prepareRateInfo(String currency);
}
