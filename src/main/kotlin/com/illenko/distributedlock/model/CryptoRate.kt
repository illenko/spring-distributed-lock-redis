package com.illenko.distributedlock.model

data class CryptoRate(
    val currency: String,
    val buy: Long,
    val sell: Long,
)