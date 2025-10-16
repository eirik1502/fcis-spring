package com.example.demo

import com.example.demo.utils.logger
import org.testcontainers.containers.PostgreSQLContainer

private class PostgreSQLContainer14 : PostgreSQLContainer<PostgreSQLContainer14>("postgres:14-alpine")


object TestContainersOppsett {
    private val logger = logger()

    private val postgresContainer = PostgreSQLContainer14().apply {
        withCommand("postgres", "-c", "wal_level=logical")
        start()
        logger.info("Started Postgres testcontainer: jdbcUrl=${this.jdbcUrl}, image=${this.dockerImageName}, containerId=${this.containerId.take(12)}")
    }

    init {
        postgresContainer.run {
            System.setProperty("spring.datasource.url", "$jdbcUrl&reWriteBatchedInserts=true")
            System.setProperty("spring.datasource.username", username)
            System.setProperty("spring.datasource.password", password)
        }
    }

    fun settOpp() {}
}
