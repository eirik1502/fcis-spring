package com.example.demo.skall

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KommandoController(
    private val kommandoService: KommandoService,
) {
    @PostMapping("/kommando")
    fun utførKomando(
        @RequestBody kommandoRequest: KommandoRequest,
    ): ResponseEntity<Boolean> {
        kommandoService.utførKommando(kommandoRequest.kommando)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/kommando/plan")
    fun planleggKommando(
        @RequestBody kommandoRequest: KommandoRequest,
    ): ResponseEntity<Plan> {
        val plan = kommandoService.planleggKommando(kommandoRequest.kommando)
        return ResponseEntity.ok(plan)
    }
}

data class KommandoRequest(
    val kommando: Kommando,
)
