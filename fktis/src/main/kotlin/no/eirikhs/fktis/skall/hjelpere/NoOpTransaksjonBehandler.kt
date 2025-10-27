package no.eirikhs.fktis.skall.hjelpere

import no.eirikhs.fktis.skall.TransaksjonBehandler
import no.eirikhs.fktis.skall.TransaksjonPropagasjon

object NoOpTransaksjonBehandler : TransaksjonBehandler {
    override fun utfør(
        propagation: TransaksjonPropagasjon,
        work: () -> Unit,
    ) = work()
}
