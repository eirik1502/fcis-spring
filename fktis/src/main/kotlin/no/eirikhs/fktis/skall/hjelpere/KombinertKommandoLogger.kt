package no.eirikhs.fktis.skall.hjelpere

import no.eirikhs.fktis.kjerne.Kommando
import no.eirikhs.fktis.kjerne.Plan
import no.eirikhs.fktis.skall.KommandoLogger
import no.eirikhs.fktis.skall.KommandoMetadata

class KombinertKommandoLogger(
    private val loggere: Collection<KommandoLogger>,
) : KommandoLogger {
    constructor(vararg logger: KommandoLogger) : this(logger.toList())

    override fun loggKommandoUtførelse(
        kommando: Kommando?,
        plan: Plan,
        suksess: Boolean,
        feil: Throwable?,
        metadata: KommandoMetadata?,
    ) {
        for (logger in loggere) {
            logger.loggKommandoUtførelse(
                kommando = kommando,
                plan = plan,
                suksess = suksess,
                feil = feil,
                metadata = metadata,
            )
        }
    }
}
