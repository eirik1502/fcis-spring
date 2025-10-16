package com.example.demo.skall

import com.example.demo.kjerne.Kommando
import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.arbeidsforhold.AaregArbeidsforhold
import com.example.demo.kjerne.arbeidsforhold.synkroniserArbeidsforhold
import com.example.demo.kjerne.sykmelding.Sykmelding
import org.springframework.stereotype.Component
import com.example.demo.kjerne.sykmelding.behandleSykmeldingHendelse
import com.example.demo.skall.eksternt.AaregKlient
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

interface KommandoUtfører {
    fun utførKommando(kommando: Kommando): Plan
}

@Component
class KommandoUtførerImpl(
    private val appContext: ApplicationContext,
) : KommandoUtfører {
    override fun utførKommando(kommando: Kommando): Plan {
        val kommandoUtførerBeskrivelse = finnKommandoUtfører(kommando::class)
        val plan = kommandoUtførerBeskrivelse.utfør(kommando)
        return plan
    }

    @Suppress("UNCHECKED_CAST")
    private fun <K : Kommando> finnKommandoUtfører(kommandoType: KClass<K>): KommandoUtførerBeskrivelse<Kommando> {
        val utfører: KommandoUtførerBeskrivelse<Kommando> =
            appContext.getBeansOfType(KommandoUtførerBeskrivelse::class.java)
                .values
                .firstOrNull { it.kommandoType.isSubclassOf(kommandoType) } as KommandoUtførerBeskrivelse<Kommando>?
                ?: error("")
        return utfører
    }
}

data class KommandoUtførerBeskrivelse<K : Kommando>(
    val kommandoType: KClass<K>,
    val utfør: (K) -> Plan
)

@Configuration
class KommandoUtførerConfig {
    @Bean
    fun behandleSykmeldingHendelseUtfører(
        sykmeldingRepository: SykmeldingRepository
    ) = KommandoUtførerBeskrivelse(Kommando.HåndterSykmeldingHendelse::class) { kommando ->
        behandleSykmeldingHendelse(
            sykmeldingId = kommando.sykmeldingId,
            sykmelding = kommando.sykmelding?.let(::konvertFraSykmeldingDTO),
            eksisterendeSykmelding = sykmeldingRepository.findBySykmeldingId(kommando.sykmeldingId),
        )
    }

    @Bean
    fun synkroniserArbeidsforholdUtfører(
        aaregKlient: AaregKlient,
    ) = KommandoUtførerBeskrivelse(Kommando.SynkroniserArbeidsforhold::class) {
        synkroniserArbeidsforhold(
            fnr = "1",
            aaregArbeidsforhold = AaregArbeidsforhold(navArbeidsforholdId = "1"),
        )
    }

}

internal fun konvertFraSykmeldingDTO(sykmelding: SykmeldingDTO): Sykmelding {
    return Sykmelding(
        sykmeldingId = sykmelding.sykmeldingId,
        fnr = sykmelding.fnr,
        fom = sykmelding.fom,
        tom = sykmelding.tom,
    )
}
