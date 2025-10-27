package no.eirikhs.fktis.kjerne

data class UtførKommandoSteg(
    val kommando: Kommando,
) : PlanSteg
