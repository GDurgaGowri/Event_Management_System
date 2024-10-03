package com.example.eventManagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling

class EventManagementApplication

fun main(args: Array<String>) {
	runApplication<EventManagementApplication>(*args)
}
