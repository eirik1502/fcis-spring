package no.eirikhs.fktis.fktis.skall.hjelpere

import no.eirikhs.fktis.fktis.skall.TransaksjonBehandler
import no.eirikhs.fktis.fktis.skall.TransaksjonPropagasjon

object NoOpTransaksjonBehandler : TransaksjonBehandler {
    override fun utfør(
        propagation: TransaksjonPropagasjon,
        work: () -> Unit,
    ) = work()
}
