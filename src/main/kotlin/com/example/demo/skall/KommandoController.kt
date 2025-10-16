package com.example.demo.skall

import com.example.demo.kjerne.Kommando
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class DummyRequest(val kommando: String)

@RestController
class KommandoController(
    private val kommandoUtfører: KommandoUtfører
) {

    @PostMapping("/dummy-kommando")
    fun utførDummyKomando(@RequestBody dummyRequest: DummyRequest): ResponseEntity<DummyRequest> {

        return ResponseEntity.ok(dummyRequest)
    }

    @PostMapping("/kommando")
    fun utførKomando(@RequestBody kommandoRequest: KommandoRequest): ResponseEntity<Boolean> {
        val plan = kommandoUtfører.utførKommando(kommandoRequest.kommando)
        return ResponseEntity.ok(true)
    }
}

data class KommandoRequest(
    val kommando: Kommando
)
