package com.example.demo.skall.kommandologg

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import org.springframework.data.annotation.Id
import java.time.Instant

data class KommandoLogg(
    @Id
    val kommandoLoggId: String? = null,
    val opprettet: Instant = Instant.now(),
    val traceId: String? = null,
    val kommandoType: String,
    val kommando: Kommando,
    val plan: Plan,
    val suksess: Boolean = true,
    val feilmelding: String? = null,
    val kildesystem: String? = null,
    val aktortype: String? = null,
    val aktorident: String? = null,
)
