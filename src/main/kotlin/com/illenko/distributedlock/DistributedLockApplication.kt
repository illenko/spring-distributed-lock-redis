package com.illenko.distributedlock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan("com.illenko.distributedlock")
class DistributedLockApplication

fun main(args: Array<String>) {
    runApplication<DistributedLockApplication>(*args)
}
