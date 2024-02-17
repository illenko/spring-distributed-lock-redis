package com.illenko.distributedlock.redis

import com.illenko.distributedlock.properties.CacheProperties
import mu.KotlinLogging
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

@Component
class LockManager(
    redissonClient: RedissonClient,
    private val cacheProperties: CacheProperties,
) {

    private val log = KotlinLogging.logger { }
    private val lock = redissonClient.getFairLock("crypto-rates-lock")

    private val lockCounter = AtomicLong()
    private val unlockCounter = AtomicLong()

    fun lock() {
        lock.tryLock(cacheProperties.leaseTime.toMillis(), TimeUnit.MILLISECONDS)
        log.info { "Locked lock: ${lockCounter.incrementAndGet()}" }
    }

    fun unlock() {
        lock.unlock()
        log.info { "Unlocked lock: ${unlockCounter.incrementAndGet()}" }
    }
}
