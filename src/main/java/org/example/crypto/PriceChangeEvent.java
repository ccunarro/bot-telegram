package org.example.crypto;

import org.springframework.context.ApplicationEvent;

public class PriceChangeEvent extends ApplicationEvent {

    String symbol;
    float oldPrice;
    float newPrice;

    public PriceChangeEvent(Object source, String symbol, float oldPrice, float newPrice) {
        super(source);
        this.symbol = symbol;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public float getNewPrice() {
        return newPrice;
    }
}
