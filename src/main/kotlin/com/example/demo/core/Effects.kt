package com.example.demo.core

import com.example.demo.model.User
import com.example.demo.skall.Sykmelding
import com.example.demo.skall.SykmeldingRegistrering
import com.example.demo.skall.SykmeldingRepository
import com.example.demo.skall.SykmeldingStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDate

data class SykmeldingDTO(
    val sykmeldingId: String,
    val fnr: String,
    val fom: LocalDate,
    val tom: LocalDate,
)

@Component
class LagreSykmeldingKommandoFactory(
    private val sykmeldingRepository: SykmeldingRepository
) {
    fun create(sykmelding: SykmeldingDTO): LagreSykmeldingKommando {
        return LagreSykmeldingKommando(sykmelding)
    }
}

sealed interface Kommando

data class LagreSykmeldingKommando(
    val sykmelding: SykmeldingDTO
) : Kommando

data class SynkroniserArbeidsforholdKommando(
    val fnr: String
) : Kommando

sealed interface Effect {
    val effektNavn: String
        get() = this::class.simpleName ?: "Unknown"
}

//@Service
//class ActionPerformer(
//    private val transactionPerformer: TransactionPerformer
//) {
//    fun performAction(effect: Effect) {
//        return when (effect) {
//            is SaveUser -> TODO()
//            is Transaction -> transactionPerformer.start(effect)
//        }
//    }
//}

//@Component
//class TransactionPerformer(
//    private val txTemplate: TransactionTemplate
//) {
//    fun start(action: Transaction) {
//        txTemplate.execute {
//
//        }
//    }
//}

data class UtførKommando(
    val kommando: Kommando,
) : Effect
data class LagreNySykmelding(
    val sykmelding: Sykmelding,
) : Effect
data class OppdaterSykmelding(
    val sykmeldingId: String,
    val sykmelding: Sykmelding,
) : Effect
data class SlettSykmelding(
    val sykmeldingId: String,
) : Effect
data class LagreNySykmeldingRegistrering(
    val registrering: SykmeldingRegistrering
) : Effect
data class SlettSykmeldingRegistreringer(
    val sykmeldingId: String,
) : Effect
data class PublishUserSavedEvent(
    val user: User
) : Effect

data class Plan(
    val effekter: List<Effect>
)

object Domain {
    fun behandleSykmeldingHendelse(
        sykmeldingId: String,
        sykmelding: Sykmelding? = null,
        eksisterendeSykmelding: Sykmelding? = null
    ): Plan {
        return if (sykmelding == null) {
            Plan(
                listOf(
                    SlettSykmelding(sykmeldingId = sykmeldingId),
                    SlettSykmeldingRegistreringer(sykmeldingId = sykmeldingId),
                )
            )
        } else if (eksisterendeSykmelding == null) {
            Plan(
                listOf(
                    OppdaterSykmelding(sykmeldingId = sykmeldingId, sykmelding = sykmelding),
                )
            )
        } else {
            Plan(
                listOf(
                    LagreNySykmelding(sykmelding = sykmelding),
                    LagreNySykmeldingRegistrering(
                        registrering = SykmeldingRegistrering(status = SykmeldingStatus.APEN)
                    ),
                    UtførKommando(
                        kommando = SynkroniserArbeidsforholdKommando(
                            fnr = eksisterendeSykmelding.fnr
                        )
                    )
                )
            )
        }
    }
}


fun synkroniserArbeidsforhold(
    fnr: String
): Plan {
    return Plan(
        listOf(
            // SynkroniserArbeidsforhold(fnr = fnr)
        )
    )
}
