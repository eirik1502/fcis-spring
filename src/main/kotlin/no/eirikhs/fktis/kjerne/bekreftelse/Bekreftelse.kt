package no.eirikhs.fktis.kjerne.bekreftelse

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

enum class BekreftelseStatus {
    BEKREFTET,
    BEKREFTET_AVVIST,
    AVBRUTT,
}

@Table("bekreftelse")
data class Bekreftelse(
    @Id
    val id: String? = null,
    val sykmeldingId: String,
    val fnr: String,
    val status: BekreftelseStatus = BekreftelseStatus.BEKREFTET,
    val arbeidssituasjon: Arbeidssituasjon? = null,
    val brukerSvar: BrukerSvar? = null,
    val opprettet: Instant,
)
