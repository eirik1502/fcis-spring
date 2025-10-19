package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import no.eirikhs.fktis.skall.rammeverk.KommandoService
import org.springframework.http.ResponseEntity
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
