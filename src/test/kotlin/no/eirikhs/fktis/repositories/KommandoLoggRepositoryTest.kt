package no.eirikhs.fktis.repositories

import no.eirikhs.fktis.KommandoTestData
import no.eirikhs.fktis.Testdata
import no.eirikhs.fktis.kommandoLogg
import no.eirikhs.fktis.skall.repositories.KommandoLoggRepository
import no.eirikhs.fktis.testoppsett.E2eTestOppsett
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class KommandoLoggRepositoryTest : E2eTestOppsett() {
    @Autowired
    private lateinit var kommandoLoggRepository: KommandoLoggRepository

    @Test
    fun `burde lagre og hente kommando logg`() {
        val lagretKommnadoLogg = kommandoLoggRepository.save(Testdata.kommandoLogg(kommando = KommandoTestData.synkroniserArbeidsforhold()))

        val hentetKommandoLogg = kommandoLoggRepository.findById(lagretKommnadoLogg.kommandoLoggId!!).get()
        hentetKommandoLogg.shouldNotBeNull()
    }
}
