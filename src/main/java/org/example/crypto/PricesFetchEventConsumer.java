package org.example.crypto;

import org.example.crypto.data.CryptoPrice;
import org.example.crypto.data.CryptoPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PricesFetchEventConsumer implements ApplicationListener<PricesFetchEvent> {

    private final CryptoPriceRepository cryptoPriceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Value("${crypto.prices.percentage.threshold}")
    private Integer PRICE_DIFFERENCE_THRESHOLD;
    @Value("${crypto.prices.fetch.rate.minutes}")
    private Long PRICES_FETCH_RATE_MINUTES;



    @Autowired
    public PricesFetchEventConsumer(CryptoPriceRepository cryptoPriceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.cryptoPriceRepository = cryptoPriceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onApplicationEvent(PricesFetchEvent event) {
        var newPrices = event.getPrices();

        //find latest prices saved on db
        List<CryptoPrice> currentPrices = cryptoPriceRepository.findPricesFromDateTime(LocalDateTime.now().minusMinutes(PRICES_FETCH_RATE_MINUTES));
        Map<String, CryptoPrice> map = currentPrices.stream().collect(Collectors.toMap(CryptoPrice::getSymbol, Function.identity()));

        List<CryptoPrice> toSave = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (PriceDto newPrice: newPrices) {
            if (map.containsKey(newPrice.getSymbol())) {
                var oldPrice = map.get(newPrice.getSymbol());
                boolean priceDecreased = newPrice.getPrice() < (oldPrice.getPrice() * (1 - (PRICE_DIFFERENCE_THRESHOLD / 100)));
                boolean priceIncreased = newPrice.getPrice() > (oldPrice.getPrice() * (1 + (PRICE_DIFFERENCE_THRESHOLD / 100)));

                if (priceDecreased || priceIncreased) {
                    PriceChangeEvent pricesFetchDtoEvent = new PriceChangeEvent(this, newPrice.getSymbol(), oldPrice.getPrice(), newPrice.getPrice());
                    applicationEventPublisher.publishEvent(pricesFetchDtoEvent);
                }
            }
            toSave.add(new CryptoPrice(newPrice.getSymbol(), newPrice.getPrice(), now));
        }
        //save all fetched prices
        cryptoPriceRepository.saveAll(toSave);
    }
}
