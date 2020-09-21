package ru.spb.piv.rates.fetcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.spb.piv.rates.fetcher.dto.CurrencyRateDto;
import ru.spb.piv.rates.fetcher.dto.HistoricalRatesDto;
import ru.spb.piv.rates.fetcher.exception.ApplicationException;
import ru.spb.piv.rates.fetcher.service.RateFetcherService;
import ru.spb.piv.rates.fetcher.service.impl.RateFetcherServiceImpl;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Test class for Rate Fetcher Service
 */
public class RateFetcherServiceImplTest {
    private static final String USD_CUR = "USD";
    private static final String EUR_CUR = "EUR";
    private static final String WRONG_CUR = "WRONG";
    private RateFetcherService rateFetcherService;

    @Before
    public void init() {
        rateFetcherService = new RateFetcherServiceImpl();
    }

    @Test
    public void shouldReturnCurrentRateForValidCurrencyWithUppercaseLetters() {
        CurrencyRateDto currentRate = rateFetcherService.fetchCurrentRate(USD_CUR.toUpperCase());
        Assert.assertNotNull(currentRate);
        Assert.assertEquals(USD_CUR.toUpperCase(), currentRate.getCode());
        Assert.assertNotNull(currentRate.getRate());
    }

    @Test
    public void shouldReturnCurrentRateForValidCurrencyWithLowercaseLetter() {
        CurrencyRateDto currentRate = rateFetcherService.fetchCurrentRate(USD_CUR.toLowerCase());
        Assert.assertNotNull(currentRate);
        Assert.assertEquals(USD_CUR.toUpperCase(), currentRate.getCode());
        Assert.assertNotNull(currentRate.getRate());
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionInFetchCurrentRateForWrongCurrency() {
        rateFetcherService.fetchCurrentRate(WRONG_CUR);
    }

    @Test
    public void shouldReturnHistoryRatesForValidCurrencyWithUppercaseLetters() {
        HistoricalRatesDto historicalRatesDto = rateFetcherService.fetchHistoricalRatesForLast30Days(EUR_CUR.toUpperCase());
        Assert.assertNotNull(historicalRatesDto);
        Map<String, BigDecimal> bpi = historicalRatesDto.getBpi();
        Assert.assertNotNull(bpi);
    }

    @Test
    public void shouldReturnHistoryRatesForValidCurrencyWithLowercaseLetter() {
        HistoricalRatesDto historicalRatesDto = rateFetcherService.fetchHistoricalRatesForLast30Days(EUR_CUR.toLowerCase());
        Assert.assertNotNull(historicalRatesDto);
        Map<String, BigDecimal> bpi = historicalRatesDto.getBpi();
        Assert.assertNotNull(bpi);
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionInFetchHistoricalRatesForWrongCurrency() {
        rateFetcherService.fetchHistoricalRatesForLast30Days(WRONG_CUR);
    }
}
