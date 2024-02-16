package com.illenko.springdistributedlockredis.service

import com.illenko.springdistributedlockredis.client.CacheClient
import com.illenko.springdistributedlockredis.client.CryptoRatesClient
import com.illenko.springdistributedlockredis.model.CryptoRate
import org.springframework.stereotype.Service

@Service
class RatesService(
    private val cryptoRatesClient: CryptoRatesClient,
    private val cacheClient: CacheClient,
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
            cacheClient.lock()
            var value = cacheClient.getCryptoRates()
            if (value == null) {
                value = cryptoRatesClient.getCryptoRates()
                cacheClient.setCryptoRates(value)
            }
            cacheClient.unlock()
            value
        } else {
            cachedValue
        }
    }
}
