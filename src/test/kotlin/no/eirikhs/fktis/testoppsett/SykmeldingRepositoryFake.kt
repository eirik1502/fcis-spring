package no.eirikhs.fktis.testoppsett

import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import no.eirikhs.fktis.skall.repositories.SykmeldingRepository
import java.util.UUID

class SykmeldingRepositoryFake :
    AbstractCrudRepositoryFake<Sykmelding, String>(
        lagId = { UUID.randomUUID().toString() },
        getEntityId = { entity -> entity.databaseId },
        setEntityId = { entity, id -> entity.copy(databaseId = id) },
    ),
    SykmeldingRepository {
    override fun findBySykmeldingId(sykmeldingId: String): Sykmelding? =
        this.entities.values.find {
            it.sykmeldingId == sykmeldingId
        }
}
