package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.bekreftelse.BrukerSvar
import no.eirikhs.fktis.kjerne.sykmelding.EksternSykmelding
import kotlin.reflect.KClass

data object NoOpKommando : Kommando

data class HåndterSykmeldingHendelse(
    val sykmeldingId: String,
    val sykmelding: EksternSykmelding? = null,
) : Kommando

data class SynkroniserArbeidsforhold(
    val fnr: String,
) : Kommando

data class ArbeidsforholdNotifikasjonKommando(
    val fnr: List<String>,
) : Kommando

data class AvbrytSykmelding(
    val sykmeldingId: String,
    val fnr: String,
) : Kommando

data class BekreftSykmelding(
    val sykmeldingId: String,
    val fnr: String,
    val brukerSvar: BrukerSvar? = null,
) : Kommando

// class KommandoRegister(
//    val kommandoVedNavn: Map<String, KClass<out Kommando>>,
// ) {
//    val navnVedKommando: Map<KClass<out Kommando>, String> =
//        kommandoVedNavn.entries.associate { (navn, kommandoType) -> kommandoType to navn }
//
//    fun finnKommandoType(navn: String): KClass<out Kommando> = kommandoVedNavn[navn] ?: error("Kommando '$navn' not found")
//
//    fun finnKommandoNavn(kommandoType: KClass<out Kommando>): String =
//        navnVedKommando[kommandoType] ?: error("Kommando type '${kommandoType.simpleName}' not found")
// }
//
// class KommandoRegisterBuilder {
//    private val kommandoVedNavn: MutableMap<String, KClass<out Kommando>> = mutableMapOf()
//
//    fun <K : Kommando> registrer(
//        navn: String,
//        kommandoType: KClass<K>,
//    ) {
//        kommandoVedNavn[navn] = kommandoType
//    }
//
//    fun bygg(): KommandoRegister = KommandoRegister(kommandoVedNavn.toMap())
// }
//
// inline fun <reified K : Kommando> KommandoRegisterBuilder.registrer(navn: String) {
//    registrer(navn, K::class)
// }
//
// fun byggKommandoRegister(builderAction: KommandoRegisterBuilder.() -> Unit): KommandoRegister {
//    val builder = KommandoRegisterBuilder()
//    builder.builderAction()
//    return builder.bygg()
// }
//
// val KOMMANDO_REGISTER =
//    byggKommandoRegister {
//        registrer<NoOpKommando>("NOOP_KOMMANDO")
//        registrer<HåndterSykmeldingHendelse>("HÅNDTER_SYKMELDING_HENDELSE")
//        registrer<SynkroniserArbeidsforhold>("SYNKRONISER_ARBEIDSFORHOLD")
//        registrer<AvbrytSykmelding>("AVBRYT_SYKMELDING")
//        registrer<BekreftSykmelding>("BEKREFT_SYKMELDING")
//    }
//
// val Kommando.kommandoNavn: String
//    get() = KOMMANDO_REGISTER.finnKommandoNavn(this::class)
