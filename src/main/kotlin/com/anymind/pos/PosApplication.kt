package com.anymind.pos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
//@EnableJpaRepositories(basePackages = ["com.anymind.pos.adaper.output.persistence.postgresql"])
class PosApplication

fun main(args: Array<String>) {
	runApplication<PosApplication>(*args)
}
