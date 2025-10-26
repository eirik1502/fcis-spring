package no.eirikhs.fktis.fktis.skall.hjelpere

import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.*
import kotlin.reflect.KClass

fun <K : Kommando> lagPlanlegger(
    kommandoType: KClass<K>,
    utfør: (K) -> Plan,
) = object : KommandoBehandler<K> {
    override val kommandoType: KClass<K> = kommandoType

    override fun utfør(kommando: K): Plan = utfør.invoke(kommando)
}

inline fun <reified K : Kommando> lagPlanlegger(noinline utfør: (K) -> Plan) = lagPlanlegger(kommandoType = K::class, utfør = utfør)

fun <E : Effekt> lagEffektUtfører(
    effektType: KClass<E>,
    utfør: (E) -> Unit,
) = object : EffektBehandler<E> {
    override val effektType: KClass<E> = effektType

    override fun utfør(effekt: E) = utfør.invoke(effekt)
}

inline fun <reified E : Effekt> lagEffektUtfører(noinline utfør: (E) -> Unit) = lagEffektUtfører(effektType = E::class, utfør = utfør)

fun lagPlanleggerRegister(planleggere: Collection<KommandoBehandler<*>>): KommandoDistributør {
    return object : KommandoDistributør {
        val kommandoBehandlerVedKommandoType: Map<KClass<out Kommando>, KommandoBehandler<*>> = planleggere.associateBy { it.kommandoType }

        override fun hentAlleBehandlere(): Set<KommandoBehandler<*>> = kommandoBehandlerVedKommandoType.values.toSet()

        override fun <K : Kommando> finnBehandler(kommandoType: KClass<K>): KommandoBehandler<K> {
            val planlegger =
                kommandoBehandlerVedKommandoType[kommandoType]
                    ?: throw IllegalArgumentException("Ingen planlegger funnet for kommando av type $kommandoType")
            @Suppress("UNCHECKED_CAST")
            return planlegger as KommandoBehandler<K>
        }
    }
}

fun lagEffektørRegister(effektører: Collection<EffektBehandler<*>>): EffektDistributør {
    return object : EffektDistributør {
        val effektUtførerVedEffektType: Map<KClass<out Effekt>, EffektBehandler<*>> = effektører.associateBy { it.effektType }

        override fun hentAlleEffektUtførere(): Set<EffektBehandler<*>> = effektUtførerVedEffektType.values.toSet()

        override fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektBehandler<E> {
            val utfører =
                effektUtførerVedEffektType[effektType]
                    ?: throw IllegalArgumentException("Ingen effektutfører funnet for effekt av type $effektType")
            @Suppress("UNCHECKED_CAST")
            return utfører as EffektBehandler<E>
        }
    }
}

fun lagKommandoService(
    planleggere: Collection<KommandoBehandler<*>>,
    effektører: Collection<EffektBehandler<*>>,
    kommandoLogger: KommandoLogger? = null,
): KommandoService =
    KommandoServiceImpl(
        kommandoDistributør = lagPlanleggerRegister(planleggere),
        effektDistributør = lagEffektørRegister(effektører),
        kommandoLogger = kommandoLogger,
    )
