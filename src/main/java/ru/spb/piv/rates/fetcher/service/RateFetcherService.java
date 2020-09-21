package ru.spb.piv.rates.fetcher.service;

import ru.spb.piv.rates.fetcher.dto.CurrencyRateDto;
import ru.spb.piv.rates.fetcher.dto.HistoricalRatesDto;

/**
 * Service for rate fetching
 */
public interface RateFetcherService {
    /**
     * fetch current rate for input currency from API
     * @param currency input currency
     * @return current rate
     */
    CurrencyRateDto fetchCurrentRate(String currency);
    /**
     * fetch historical rates for input currency from API
     * @param currency input currency
     * @return historical rates
     */
    HistoricalRatesDto fetchHistoricalRatesForLast30Days(String currency);
}
