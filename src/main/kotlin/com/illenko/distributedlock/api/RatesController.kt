package com.illenko.distributedlock.api

import com.illenko.distributedlock.model.CryptoRate
import com.illenko.distributedlock.service.RatesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RatesController(
    private val service: RatesService,
) {

    @GetMapping("/crypto-rates")
    fun getCryptoRates(): List<CryptoRate> = service.getCryptoRatesCachedDistributedLock()

}
