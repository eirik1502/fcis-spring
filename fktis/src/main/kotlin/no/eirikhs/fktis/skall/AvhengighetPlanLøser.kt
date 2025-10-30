package no.eirikhs.fktis.skall

import no.eirikhs.fktis.kjerne.Avhengighet
import no.eirikhs.fktis.kjerne.AvhengighetPlan
import no.eirikhs.fktis.kjerne.LøstAvhengighet
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class AvhengighetPlanLøser(
    val avhengighetLøsere: Collection<AvhengighetLøser<*, *>>,
) {
    fun <R : Any> løs(plan: AvhengighetPlan<R>): R {
        val løsteAvhengigheter: Map<KProperty1<R, *>, Any?> =
            plan.avhengigheter.mapValues { (_, avhengighet) ->
                val løser = finnAvhengighetLøserFor(avhengighet)
                løser.løs(avhengighet)
            }
        return konstruerFraAvhengigheter(
            klasse = plan.klasse,
            løstAvhengighetBindinger = løsteAvhengigheter,
        )
    }

    private fun <R> finnAvhengighetLøserFor(avhengighet: Avhengighet<R>): AvhengighetLøser<Avhengighet<R>, R> {
        val løser =
            avhengighetLøsere.find { it.avhengighetType.isInstance(avhengighet) }
                ?: error("Fant ingen avhengighets løser for avhengighetstype: ${avhengighet::class.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return løser as AvhengighetLøser<Avhengighet<R>, R>
    }
}

fun <T : Any> konstruerFraAvhengigheter(
    klasse: KClass<out T>,
    løstAvhengighetBindinger: Map<KProperty1<T, *>, Any?>,
): T {
    val konstruktør = klasse.constructors.first()
    val paramsVedNavn =
        konstruktør.parameters.associateBy { param ->
            checkNotNull(param.name) {
                "Konstruktør parameter er definert uten navn" +
                    mapOf(
                        "klasse" to klasse.simpleName,
                        "parameter" to param,
                        "konstruktør" to konstruktør.name,
                    )
            }
        }
    val avhengighetVerdierVedNavn =
        løstAvhengighetBindinger
            .mapKeys { (prop, _) -> prop.name }

    require(paramsVedNavn.keys == avhengighetVerdierVedNavn.keys) {
        "Konstruktør parametere og avhengighet verdier stemmer ikke overens" +
            mapOf(
                "klasse" to klasse.simpleName,
                "manglende" to paramsVedNavn.keys - avhengighetVerdierVedNavn.keys,
                "overflødige" to avhengighetVerdierVedNavn.keys to paramsVedNavn.keys,
            )
    }

    val verdierVedParam =
        paramsVedNavn
            .map { (navn, param) ->
                param to avhengighetVerdierVedNavn[navn]!!
            }.toMap()

    return konstruktør.callBy(verdierVedParam)
}
