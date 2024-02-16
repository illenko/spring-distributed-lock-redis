package com.illenko.springdistributedlockredis.api

import com.illenko.springdistributedlockredis.model.CryptoRate
import com.illenko.springdistributedlockredis.service.RatesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RatesController(
    private val service: RatesService,
) {

    @GetMapping("/crypto-rates")
    fun getCryptoRates(): List<CryptoRate> = service.getCryptoRatesCachedDistributedLock()

}