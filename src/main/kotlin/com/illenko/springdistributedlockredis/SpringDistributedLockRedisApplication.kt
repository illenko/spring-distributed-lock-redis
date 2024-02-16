package com.illenko.springdistributedlockredis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.illenko.springdistributedlockredis")
class SpringDistributedLockRedisApplication

fun main(args: Array<String>) {
    runApplication<SpringDistributedLockRedisApplication>(*args)
}
