package no.eirikhs.fktis.fktis.skall.hjelpere

import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.*
import kotlin.reflect.KClass

fun <K : Kommando> lagPlanlegger(
    kommandoType: KClass<K>,
    utfør: (K) -> Plan,
) = object : Planlegger<K> {
    override val kommandoType: KClass<K> = kommandoType

    override fun utfør(kommando: K): Plan = utfør.invoke(kommando)
}

inline fun <reified K : Kommando> lagPlanlegger(noinline utfør: (K) -> Plan) = lagPlanlegger(kommandoType = K::class, utfør = utfør)

fun <E : Effekt> lagEffektUtfører(
    effektType: KClass<E>,
    utfør: (E) -> Unit,
) = object : EffektUtfører<E> {
    override val effektType: KClass<E> = effektType

    override fun utfør(effekt: E) = utfør.invoke(effekt)
}

inline fun <reified E : Effekt> lagEffektUtfører(noinline utfør: (E) -> Unit) = lagEffektUtfører(effektType = E::class, utfør = utfør)

fun lagPlanleggerRegister(planleggere: Collection<Planlegger<*>>): PlanleggerRegister {
    return object : PlanleggerRegister {
        val planleggerVedKommandoType: Map<KClass<out Kommando>, Planlegger<*>> = planleggere.associateBy { it.kommandoType }

        override fun hentAllePlanleggere(): Set<Planlegger<*>> = planleggerVedKommandoType.values.toSet()

        override fun <K : Kommando> finnPlanlegger(kommandoType: KClass<K>): Planlegger<K> {
            val planlegger =
                planleggerVedKommandoType[kommandoType]
                    ?: throw IllegalArgumentException("Ingen planlegger funnet for kommando av type $kommandoType")
            @Suppress("UNCHECKED_CAST")
            return planlegger as Planlegger<K>
        }
    }
}

fun lagEffektørRegister(effektører: Collection<EffektUtfører<*>>): EffektUtførerRegister {
    return object : EffektUtførerRegister {
        val effektUtførerVedEffektType: Map<KClass<out Effekt>, EffektUtfører<*>> = effektører.associateBy { it.effektType }

        override fun hentAlleEffektUtførere(): Set<EffektUtfører<*>> = effektUtførerVedEffektType.values.toSet()

        override fun <E : Effekt> finnEffektUtfører(effektType: KClass<E>): EffektUtfører<E> {
            val utfører =
                effektUtførerVedEffektType[effektType]
                    ?: throw IllegalArgumentException("Ingen effektutfører funnet for effekt av type $effektType")
            @Suppress("UNCHECKED_CAST")
            return utfører as EffektUtfører<E>
        }
    }
}

fun lagKommandoService(
    planleggere: Collection<Planlegger<*>>,
    effektører: Collection<EffektUtfører<*>>,
    kommandoLogger: KommandoLogger? = null,
): KommandoService =
    KommandoServiceImpl(
        planleggerRegister = lagPlanleggerRegister(planleggere),
        effektUtførerRegister = lagEffektørRegister(effektører),
        kommandoLogger = kommandoLogger,
    )
