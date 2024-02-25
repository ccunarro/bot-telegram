package org.example.crypto;

public class PriceDto {

    String symbol;
    float price;

    public PriceDto(String symbol, float price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getPrice() {
        return price;
    }
}
