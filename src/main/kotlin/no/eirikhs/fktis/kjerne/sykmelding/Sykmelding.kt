package no.eirikhs.fktis.kjerne.sykmelding

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class Sykmelding(
    @Id
    val databaseId: String? = null,
    val sykmeldingId: String,
    val fnr: String,
    val fom: LocalDate,
    val tom: LocalDate,
)
