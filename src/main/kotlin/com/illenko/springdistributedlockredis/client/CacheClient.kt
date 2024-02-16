package com.illenko.springdistributedlockredis.client

import com.illenko.springdistributedlockredis.model.CryptoRate
import com.illenko.springdistributedlockredis.properties.CacheProperties
import mu.KotlinLogging
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

@Component
class CacheClient(
    redissonClient: RedissonClient,
    private val cacheProperties: CacheProperties,
) {

    private val log = KotlinLogging.logger { }

    private val cache = redissonClient.getBucket<List<CryptoRate>>("crypto-rates")
    private val lock = redissonClient.getFairLock("crypto-rates-lock")

    private val getCounter = AtomicLong()
    private val setCounter = AtomicLong()
    private val lockCounter = AtomicLong()
    private val unlockCounter = AtomicLong()

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

    fun lock() {
        lock.tryLock(cacheProperties.leaseTime.toMillis(), TimeUnit.MILLISECONDS)
        log.info { "Locked lock: ${lockCounter.incrementAndGet()}" }
    }

    fun unlock() {
        lock.unlock()
        log.info { "Unlocked lock: ${unlockCounter.incrementAndGet()}" }
    }
}