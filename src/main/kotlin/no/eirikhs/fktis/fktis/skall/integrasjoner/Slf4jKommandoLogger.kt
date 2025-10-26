package no.eirikhs.fktis.fktis.skall.integrasjoner

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.KommandoLogger
import no.eirikhs.fktis.fktis.skall.KommandoMetadata
import org.slf4j.Logger

class Slf4jKommandoLogger(
    private val logger: Logger,
) : KommandoLogger {
    override fun loggKommandoUtførelse(
        kommando: Kommando?,
        plan: Plan,
        suksess: Boolean,
        feil: Throwable?,
        metadata: KommandoMetadata?,
    ) {
        val message =
            "Kommando utført" +
                mapOf(
                    "kommando" to kommando,
                    "plan" to plan,
                    "metadata" to metadata,
                )
        if (suksess) {
            logger.info(message)
        } else {
            logger.error(message, feil)
        }
    }
}
