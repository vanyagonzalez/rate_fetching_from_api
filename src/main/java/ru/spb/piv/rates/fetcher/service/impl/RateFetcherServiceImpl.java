package ru.spb.piv.rates.fetcher.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import ru.spb.piv.rates.fetcher.dto.CurrencyRateDto;
import ru.spb.piv.rates.fetcher.dto.CurrentPriceDto;
import ru.spb.piv.rates.fetcher.dto.HistoricalRatesDto;
import ru.spb.piv.rates.fetcher.exception.ApplicationException;
import ru.spb.piv.rates.fetcher.service.RateFetcherService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * RateFetcherService implementation
 */
public class RateFetcherServiceImpl implements RateFetcherService {
    private static final int DATE_DELTA = -30;
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    private static String CURRENT_PRICE_API_URL = "https://api.coindesk.com/v1/bpi/currentprice/%s.json";
    private static String HISTORICAL_API_URL = "https://api.coindesk.com/v1/bpi/historical/close.json?start=%s&end=%s&currency=%s";

    private final HttpClient client;

    public RateFetcherServiceImpl() {
        this.client = HttpClientBuilder.create().build();
    }


    public CurrencyRateDto fetchCurrentRate(String currency) {
        try {
            HttpResponse response = client.execute(new HttpGet(String.format(CURRENT_PRICE_API_URL, currency)));
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            if (statusCode == HttpStatus.SC_OK) {
                CurrentPriceDto currentPriceDto = OBJECT_MAPPER.readValue(responseBody, CurrentPriceDto.class);
                return currentPriceDto.getBpi().get(currency.toUpperCase());
            } else {
                throw new ApplicationException(responseBody);
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    public HistoricalRatesDto fetchHistoricalRatesForLast30Days(String currency) {
        Calendar cal = Calendar.getInstance();
        Date endTime = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, DATE_DELTA);
        Date startTime = cal.getTime();
        String url = String.format(HISTORICAL_API_URL, DF.format(startTime), DF.format(endTime), currency);
        try {
            HttpResponse response = client.execute(new HttpGet(url));
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            if (statusCode == HttpStatus.SC_OK) {
                return OBJECT_MAPPER.readValue(responseBody, HistoricalRatesDto.class);
            } else {
                throw new ApplicationException(responseBody);
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
