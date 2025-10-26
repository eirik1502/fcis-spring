package no.eirikhs.fktis.fktis.skall.hjelpere

import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.Plan
import no.eirikhs.fktis.fktis.skall.KommandoLogger
import no.eirikhs.fktis.fktis.skall.KommandoMetadata

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
