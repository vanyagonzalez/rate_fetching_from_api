package ru.spb.piv.rates.fetcher.service.impl;

import ru.spb.piv.rates.fetcher.dto.CurrencyRateDto;
import ru.spb.piv.rates.fetcher.dto.CurrencyRatesInfoDto;
import ru.spb.piv.rates.fetcher.dto.HistoricalRatesDto;
import ru.spb.piv.rates.fetcher.service.RateFetcherService;
import ru.spb.piv.rates.fetcher.service.RateInfoPreparerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RateInfoPreparerService implementation
 */
public class RateInfoPreparerServiceImpl implements RateInfoPreparerService {
    private final RateFetcherService rateFetcherService;

    public RateInfoPreparerServiceImpl(RateFetcherService rateFetcherService) {
        this.rateFetcherService = rateFetcherService;
    }

    public CurrencyRatesInfoDto prepareRateInfo(String currency) {
        CurrencyRateDto currentRate = rateFetcherService.fetchCurrentRate(currency);

        HistoricalRatesDto historicalRates = rateFetcherService.fetchHistoricalRatesForLast30Days(currency);
        List<BigDecimal> rates = new ArrayList<BigDecimal>(historicalRates.getBpi().values());
        Collections.sort(rates);
        BigDecimal minRate = rates.get(0);
        Collections.sort(rates, Collections.<BigDecimal>reverseOrder());
        BigDecimal maxRate = rates.get(0);

        return new CurrencyRatesInfoDto(currentRate.getCode(), currentRate.getRate(), minRate, maxRate);

    }
}
