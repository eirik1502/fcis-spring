package no.eirikhs.fktis.fktis.skall.integrasjoner

import no.eirikhs.fktis.fktis.skall.TransaksjonBehandler
import no.eirikhs.fktis.fktis.skall.TransaksjonPropagasjon
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

class SpringTransaksjonBehandler(
    txManager: PlatformTransactionManager,
) : TransaksjonBehandler {
    private val txTemplate = TransactionTemplate(txManager)

    override fun utfør(
        propagation: TransaksjonPropagasjon,
        work: () -> Unit,
    ) {
        txTemplate.propagationBehavior =
            when (propagation) {
                TransaksjonPropagasjon.PÅKREVD -> TransactionTemplate.PROPAGATION_REQUIRED
                TransaksjonPropagasjon.NESTET -> TransactionTemplate.PROPAGATION_NESTED
            }
        txTemplate.execute {
            work()
        }
    }
}
