package com.illenko.distributedlock.client

import com.illenko.distributedlock.model.CryptoRate
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class CryptoRatesClient {

    private val log = KotlinLogging.logger { }
    private val requestCounter = AtomicLong()

    fun getCryptoRates(): List<CryptoRate> {
        log.info { "Requested crypto rates from external system: ${requestCounter.incrementAndGet()}" }
        Thread.sleep(500)
        return listOf(
            CryptoRate(
                currency = "BTC",
                buy = 5000000,
                sell = 4900000,
            ),
            CryptoRate(
                currency = "ETH",
                buy = 270000,
                sell = 260000,
            )
        )
    }
}
