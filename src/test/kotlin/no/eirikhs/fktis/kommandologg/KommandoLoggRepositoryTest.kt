package no.eirikhs.fktis.kommandologg

import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.kommandoLogg
import no.eirikhs.fktis.skall.kommandologg.KommandoLoggRepository
import no.eirikhs.fktis.testoppsett.E2eTestOppsett
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
