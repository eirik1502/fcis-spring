package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.skall.KommandoRegister
import no.eirikhs.fktis.skall.RegistrertKommando

val KOMMANDO_REGISTER =
    KommandoRegister(
        RegistrertKommando(
            navn = "HÅNDTER_SYKMELDING_HENDELSE",
            kommandoType = HåndterSykmeldingHendelseKommando::class,
            avhengigheter = ::håndterSykmeldingHendelseAvhengigheter,
            planlegger = ::håndterSykmeldingHendelsePlanlegger,
        ),
    )
