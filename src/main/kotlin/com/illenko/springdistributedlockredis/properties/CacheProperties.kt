package com.illenko.springdistributedlockredis.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("cache")
data class CacheProperties(
    val ttl: Duration,
    val leaseTime: Duration,
)