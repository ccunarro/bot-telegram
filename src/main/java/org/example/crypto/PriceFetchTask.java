package org.example.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class PriceFetchTask {

    private final RestTemplate restTemplate;
    private ApplicationEventPublisher applicationEventPublisher;
    private final static String FETCH_URL = "https://api.mexc.com/api/v3/ticker/price";


    @Autowired
    public PriceFetchTask(RestTemplate restTemplate, ApplicationEventPublisher applicationEventPublisher) {
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private static class PriceDto {
        String symbol;
        String price;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    @Scheduled(fixedRateString="${crypto.prices.fetch.rate.minutes}", timeUnit = TimeUnit.MINUTES)
    public void fetchPrices() {
        ResponseEntity<PriceDto[]> response =
                restTemplate.getForEntity(
                        FETCH_URL,
                        PriceDto[].class);
        List<org.example.crypto.PriceDto> prices = Arrays.stream(response.getBody()).map(p -> new org.example.crypto.PriceDto(p.getSymbol(), Float.valueOf(p.getPrice()))).toList();

        PricesFetchEvent pricesFetchEvent = new PricesFetchEvent(this, prices, LocalDateTime.now());
        applicationEventPublisher.publishEvent(pricesFetchEvent);

    }
}
