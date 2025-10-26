package no.eirikhs.fktis.fktis.skall.spring

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.skall.KommandoBehandler
import no.eirikhs.fktis.fktis.skall.KommandoDistributør
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class SpringContextKommandoDistributør(
    private val appContext: ApplicationContext,
) : KommandoDistributør {
    override fun hentAlleBehandlere(): Set<KommandoBehandler<*>> =
        appContext
            .getBeansOfType(KommandoBehandler::class.java)
            .values
            .toSet()

    override fun <K : Kommando> finnBehandler(kommandoType: KClass<K>): KommandoBehandler<K> {
        val allePlanleggere =
            appContext
                .getBeansOfType(KommandoBehandler::class.java)
                .values

        @Suppress("UNCHECKED_CAST")
        val kommandoBehandler: KommandoBehandler<Kommando> =
            allePlanleggere.firstOrNull { it.kommandoType.isSubclassOf(kommandoType) } as KommandoBehandler<Kommando>?
                ?: error("Ingen Planleggere registrert for kommando type ${kommandoType.simpleName}")
        @Suppress("UNCHECKED_CAST")
        return kommandoBehandler as KommandoBehandler<K>
    }
}
