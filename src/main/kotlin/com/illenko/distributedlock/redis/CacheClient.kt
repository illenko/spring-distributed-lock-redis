package com.illenko.distributedlock.redis

import com.illenko.distributedlock.model.CryptoRate
import com.illenko.distributedlock.properties.CacheProperties
import mu.KotlinLogging
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class CacheClient(
    redissonClient: RedissonClient,
    private val cacheProperties: CacheProperties,
) {

    private val log = KotlinLogging.logger { }

    private val cache = redissonClient.getBucket<List<CryptoRate>>("crypto-rates")

    private val getCounter = AtomicLong()
    private val setCounter = AtomicLong()

    fun getCryptoRates(): List<CryptoRate>? =
        if (cache.isExists) {
            log.info { "Retrieving crypto rates from cache: ${getCounter.incrementAndGet()}" }
            cache.get()
        } else {
            null
        }

    fun setCryptoRates(rates: List<CryptoRate>) {
        log.info { "Setting crypto rates in cache: ${setCounter.incrementAndGet()}" }
        cache[rates] = cacheProperties.ttl
    }
}
