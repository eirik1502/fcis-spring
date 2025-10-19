package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.UtførKommando
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StandardEffektUtførere {
    @Bean
    fun utførKommandoEffektUtfører(kommandoService: KommandoService) =
        lagEffektUtfører<UtførKommando> { effekt ->
            kommandoService.utførKommando(effekt.kommando)
        }
}
