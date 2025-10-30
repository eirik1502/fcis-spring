package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.kjerne.arbeidsforhold.AaregArbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding

data class FinnSykmelding(
    val sykmeldingId: String,
) : Avhengighet<Sykmelding?>

data class FinnAlleSykmeldinger(
    val fnr: String,
) : Avhengighet<List<Sykmelding>>

data class HentMangeAaregArbeidsforhold(
    val fnr: String,
) : Avhengighet<List<AaregArbeidsforhold>>
