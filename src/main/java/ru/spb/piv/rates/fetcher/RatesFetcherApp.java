package ru.spb.piv.rates.fetcher;

import ru.spb.piv.rates.fetcher.dto.CurrencyRatesInfoDto;
import ru.spb.piv.rates.fetcher.exception.ApplicationException;
import ru.spb.piv.rates.fetcher.service.RateFetcherService;
import ru.spb.piv.rates.fetcher.service.RateInfoPreparerService;
import ru.spb.piv.rates.fetcher.service.impl.RateFetcherServiceImpl;
import ru.spb.piv.rates.fetcher.service.impl.RateInfoPreparerServiceImpl;

import java.util.Scanner;

/**
 * Main application class
 */
public class RatesFetcherApp {
    private static final String SELECT_CURRENCY_MSG = "Select currency for fetching rate info:";
    private final RateInfoPreparerService rateInfoPreparerService;

    private RatesFetcherApp(RateInfoPreparerService rateInfoPreparerService) {
        this.rateInfoPreparerService = rateInfoPreparerService;
    }

    public static void main(String[] args) {
        RatesFetcherApp app = init();
        try {
            System.out.println(app.getRatesInfoByCurrencyFromInputStream());
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static RatesFetcherApp init() {
        RateFetcherService rateFetcherService = new RateFetcherServiceImpl();
        RateInfoPreparerService rateInfoPreparerService = new RateInfoPreparerServiceImpl(rateFetcherService);
        return new RatesFetcherApp(rateInfoPreparerService);
    }

    private CurrencyRatesInfoDto getRatesInfoByCurrencyFromInputStream() {
        String currency = getCurrencyFromInputSteam();
        return rateInfoPreparerService.prepareRateInfo(currency);
    }

    private String getCurrencyFromInputSteam() {
        System.out.println(SELECT_CURRENCY_MSG);
        Scanner in = new Scanner(System.in);
        return in.nextLine().trim();
    }
}
