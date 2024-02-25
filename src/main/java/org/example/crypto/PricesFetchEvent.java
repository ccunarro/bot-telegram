package org.example.crypto;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.List;

public class PricesFetchEvent extends ApplicationEvent {

    List<PriceDto> prices;
    LocalDateTime date;

    public PricesFetchEvent(Object source, List<PriceDto> prices, LocalDateTime date) {
        super(source);
        this.prices = prices;
        this.date = date;
    }

    public List<PriceDto> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceDto> prices) {
        this.prices = prices;
    }
}
