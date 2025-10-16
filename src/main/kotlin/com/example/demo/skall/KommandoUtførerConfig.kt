package com.example.demo.skall

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.arbeidsforhold.AaregArbeidsforhold
import com.example.demo.kjerne.arbeidsforhold.synkroniserArbeidsforhold
import com.example.demo.kjerne.sykmelding.Sykmelding
import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
import com.example.demo.skall.eksternt.AaregKlient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass

data class KommandoUtfører<K : Kommando>(
    val kommandoType: KClass<K>,
    private val utfør: (K) -> Plan,
) {
    fun utfør(kommando: K): Plan = utfør.invoke(kommando)
}

@Configuration
class KommandoUtførerConfig {
    @Bean
    fun behandleSykmeldingHendelseUtfører(sykmeldingRepository: SykmeldingRepository) =
        KommandoUtfører(Kommando.HåndterSykmeldingHendelse::class) { kommando ->
            behandleSykmeldingHendelse(
                sykmeldingId = kommando.sykmeldingId,
                sykmelding = kommando.sykmelding?.let(::konvertFraSykmeldingDTO),
                eksisterendeSykmelding = sykmeldingRepository.findBySykmeldingId(kommando.sykmeldingId),
            )
        }

    @Bean
    fun synkroniserArbeidsforholdUtfører(aaregKlient: AaregKlient) =
        KommandoUtfører(Kommando.SynkroniserArbeidsforhold::class) {
            synkroniserArbeidsforhold(
                fnr = "1",
                aaregArbeidsforhold = AaregArbeidsforhold(navArbeidsforholdId = "1"),
            )
        }
}

internal fun konvertFraSykmeldingDTO(sykmelding: SykmeldingDTO): Sykmelding =
    Sykmelding(
        sykmeldingId = sykmelding.sykmeldingId,
        fnr = sykmelding.fnr,
        fom = sykmelding.fom,
        tom = sykmelding.tom,
    )
