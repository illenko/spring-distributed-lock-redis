package com.illenko.springdistributedlockredis.model

data class CryptoRate(
    val currency: String,
    val buy: Long,
    val sell: Long,
)