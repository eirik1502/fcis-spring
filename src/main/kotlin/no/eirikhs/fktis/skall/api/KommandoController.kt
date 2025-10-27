package no.eirikhs.fktis.skall.api

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import no.eirikhs.fktis.skall.KommandoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KommandoController(
    private val kommandoService: no.eirikhs.fktis.skall.KommandoService,
) {
    @PostMapping("/kommando")
    fun utførKomando(
        @RequestBody kommando: no.eirikhs.fktis.kjerne.Kommando,
    ): ResponseEntity<Boolean> {
        kommandoService.utførKommando(kommando)
        return ResponseEntity.ok(true)
    }

    @PostMapping("/kommando/plan")
    fun planleggKommando(
        @RequestBody kommando: no.eirikhs.fktis.kjerne.Kommando,
    ): ResponseEntity<Plan> {
        val plan = kommandoService.planleggKommando(kommando)
        return ResponseEntity.ok(plan)
    }
}
