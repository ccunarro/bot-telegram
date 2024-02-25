package org.example.crypto.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoPriceRepository extends CrudRepository<CryptoPrice, Long> {

    @Query(value = "SELECT cp FROM crypto_price cp WHERE cp.created > :from")
    List<CryptoPrice> findPricesFromDateTime(@Param("from") LocalDateTime from);
}
