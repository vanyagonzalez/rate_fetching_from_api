package ru.spb.piv.rates.fetcher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ru.spb.piv.rates.fetcher.dto.CurrencyRateDto;
import ru.spb.piv.rates.fetcher.dto.CurrencyRatesInfoDto;
import ru.spb.piv.rates.fetcher.dto.HistoricalRatesDto;
import ru.spb.piv.rates.fetcher.exception.ApplicationException;
import ru.spb.piv.rates.fetcher.service.RateFetcherService;
import ru.spb.piv.rates.fetcher.service.RateInfoPreparerService;
import ru.spb.piv.rates.fetcher.service.impl.RateInfoPreparerServiceImpl;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for Rate Info Preparer Service
 */
public class RateInfoPreparerServiceImplTest {
    private static final String USD_CUR = "USD";
    private static final String WRONG_CUR = "WRONG";
    private RateInfoPreparerService rateInfoPreparerService;
    private RateFetcherService rateFetcherService;

    @Before
    public void init() {
        rateFetcherService = mock(RateFetcherService.class);
        rateInfoPreparerService = new RateInfoPreparerServiceImpl(rateFetcherService);
    }

    @Test
    public void shouldReturnCorrectRateInfo() {
        when(rateFetcherService.fetchCurrentRate(USD_CUR)).thenReturn(new CurrencyRateDto(USD_CUR, BigDecimal.ONE));
        HistoricalRatesDto historicalRatesDto = new HistoricalRatesDto();
        historicalRatesDto.getBpi().put("date1", BigDecimal.ZERO);
        historicalRatesDto.getBpi().put("date2", BigDecimal.ONE);
        historicalRatesDto.getBpi().put("date3", BigDecimal.TEN);
        when(rateFetcherService.fetchHistoricalRatesForLast30Days(USD_CUR)).thenReturn(historicalRatesDto);

        CurrencyRatesInfoDto currencyRatesInfoDto = rateInfoPreparerService.prepareRateInfo(USD_CUR);
        Assert.assertNotNull(currencyRatesInfoDto);
        Assert.assertEquals(USD_CUR, currencyRatesInfoDto.getCode());
        Assert.assertEquals(BigDecimal.ONE, currencyRatesInfoDto.getCurrentRate());
        Assert.assertEquals(BigDecimal.ZERO, currencyRatesInfoDto.getMinRate());
        Assert.assertEquals(BigDecimal.TEN, currencyRatesInfoDto.getMaxRate());
    }

    @Test(expected = ApplicationException.class)
    public void shouldThrowExceptionForWrongCurrency() {
        when(rateFetcherService.fetchCurrentRate(WRONG_CUR)).thenThrow(new ApplicationException("exception"));
        rateInfoPreparerService.prepareRateInfo(WRONG_CUR);
    }
}
