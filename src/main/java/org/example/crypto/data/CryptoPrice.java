package org.example.crypto.data;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "crypto_price")
public class CryptoPrice {

    @Id
    @GeneratedValue
    private Long id;

    private String symbol;
    private Float price;
    private LocalDateTime created;

    public CryptoPrice(String symbol, Float price, LocalDateTime created) {
        this.symbol = symbol;
        this.price = price;
        this.created = created;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
