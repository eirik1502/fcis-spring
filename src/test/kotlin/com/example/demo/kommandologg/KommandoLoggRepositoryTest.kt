package com.example.demo.kommandologg

import com.example.demo.Testdata
import com.example.demo.kommandoLogg
import com.example.demo.skall.kommandologg.KommandoLoggRepository
import com.example.demo.testoppsett.E2eTestOppsett
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class KommandoLoggRepositoryTest : E2eTestOppsett() {
    @Autowired
    private lateinit var kommandoLoggRepository: KommandoLoggRepository

    @Test
    fun `burde lagre og hente kommando logg`() {
        val lagretKommnadoLogg = kommandoLoggRepository.save(Testdata.kommandoLogg())

        val hentetKommandoLogg = kommandoLoggRepository.findById(lagretKommnadoLogg.kommandoLoggId!!).get()
        hentetKommandoLogg.shouldNotBeNull()
    }
}
