package no.eirikhs.fktis.skall.porter

import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SykmeldingRepository : CrudRepository<Sykmelding, String> {
    fun findBySykmeldingId(sykmeldingId: String): Sykmelding?
}
