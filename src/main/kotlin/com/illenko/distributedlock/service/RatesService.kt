package com.illenko.distributedlock.service

import com.illenko.distributedlock.redis.CacheClient
import com.illenko.distributedlock.client.CryptoRatesClient
import com.illenko.distributedlock.model.CryptoRate
import com.illenko.distributedlock.redis.LockManager
import org.springframework.stereotype.Service

@Service
class RatesService(
    private val cryptoRatesClient: CryptoRatesClient,
    private val cacheClient: CacheClient,
    private val lockManager: LockManager,
) {

    fun getCryptoRatesNoCache(): List<CryptoRate> = cryptoRatesClient.getCryptoRates()

    fun getCryptoRatesCached(): List<CryptoRate> {
        val cachedValue = cacheClient.getCryptoRates()
        return if (cachedValue == null) {
            val realValue = cryptoRatesClient.getCryptoRates()
            cacheClient.setCryptoRates(realValue)
            realValue
        } else {
            cachedValue
        }
    }

    fun getCryptoRatesCachedDistributedLock(): List<CryptoRate> {
        val cachedValue = cacheClient.getCryptoRates()
        return if (cachedValue == null) {
            lockManager.lock()
            var value = cacheClient.getCryptoRates()
            if (value == null) {
                value = cryptoRatesClient.getCryptoRates()
                cacheClient.setCryptoRates(value)
            }
            lockManager.unlock()
            value
        } else {
            cachedValue
        }
    }
}
