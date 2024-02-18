package com.illenko.distributedlock.redis

import com.illenko.distributedlock.client.CryptoRatesClient
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

@Component
class CacheManager(
    private val cryptoRatesClient: CryptoRatesClient,
    private val cacheClient: CacheClient,
) {

    private val log = KotlinLogging.logger { }
    private val cacheRefillCounter = AtomicLong()

    @Scheduled(fixedRateString = "\${cache.rate}", timeUnit = TimeUnit.SECONDS)
    fun cacheRates() {
        cacheClient.setCryptoRates(cryptoRatesClient.getCryptoRates())
        log.info { "Cached value by scheduled job: ${cacheRefillCounter.incrementAndGet()}" }
    }
}
