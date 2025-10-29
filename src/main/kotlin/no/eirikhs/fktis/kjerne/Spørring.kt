package no.eirikhs.fktis.kjerne

import no.eirikhs.fktis.kjerne.arbeidsforhold.AaregArbeidsforhold
import no.eirikhs.fktis.kjerne.arbeidsforhold.synkroniserArbeidsforhold
import no.eirikhs.fktis.kjerne.sykmelding.Sykmelding
import java.time.LocalDate
import kotlin.reflect.KProperty

abstract class Spørring<R> {
    var resultat: R? = null
}

data class FinnSykmelding(
    val sykmeldingId: String,
) : Spørring<Sykmelding>()

data class FinnAlleSykmeldinger(
    val fnr: String,
) : Spørring<List<Sykmelding>>()

data class HentMangeAaregArbeidsforhold(
    val fnr: String,
) : Spørring<List<AaregArbeidsforhold>>()

fun populerSpørringBeskrivelse(spørringBeskrivelse: SpørringBeskrivelse) {
    for (spørring in spørringBeskrivelse.spørringer) {
        when (spørring) {
            is HentMangeAaregArbeidsforhold -> {
                spørring.resultat =
                    listOf(
                        AaregArbeidsforhold(
                            navArbeidsforholdId = "1",
                            orgnummer = "123",
                            juridiskOrgnummer = "456",
                            orgnavn = "org",
                            fom = LocalDate.now(),
                            tom = null,
                            arbeidsforholdType = null,
                        ),
                    )
            }
            is FinnSykmelding -> {
                spørring.resultat =
                    Sykmelding(
                        sykmeldingId = spørring.sykmeldingId,
                        fnr = "fnr",
                        fom = LocalDate.now(),
                        tom = LocalDate.now().plusDays(10),
                    )
            }
            is FinnAlleSykmeldinger -> {
                spørring.resultat =
                    listOf(
                        Sykmelding(
                            sykmeldingId = "1",
                            fnr = spørring.fnr,
                            fom = LocalDate.now(),
                            tom = LocalDate.now().plusDays(10),
                        ),
                    )
            }
            else -> error("Ukjent spørringstype: ${spørring::class.simpleName}")
        }
    }
}

data class SpørringBeskrivelse(
    val spørringer: List<Spørring<*>>,
    val bind: () -> Plan,
)

class SpørringBygger {
    private val spørringer: MutableList<Spørring<*>> = mutableListOf()
    private var bindFunksjon: (() -> Plan)? = null

    infix fun <E> spørring(spørring: Spørring<E>): Lazy<E> {
        spørringer.add(spørring)
        return lazy { spørring.resultat ?: error("Spørring ikke gjennomført") }
    }

    operator fun <E> Spørring<E>.unaryPlus(): Lazy<E> = spørring(this)

    fun bind(bindFunksjon: () -> Plan) {
        this.bindFunksjon = bindFunksjon
    }

    fun bygg(): SpørringBeskrivelse =
        SpørringBeskrivelse(
            spørringer = spørringer.toList(),
            bind = bindFunksjon ?: error("Bind funksjon må settes"),
        )
}

fun byggSpørring(block: SpørringBygger.() -> Unit): SpørringBeskrivelse {
    val builder = SpørringBygger()
    block(builder)
    return builder.bygg()
}

fun synkroniserArbeidsforholdSpørring(kommando: SynkroniserArbeidsforhold) =
    byggSpørring {
        val arbeidsforhold by +HentMangeAaregArbeidsforhold(fnr = kommando.fnr)
        bind {
            synkroniserArbeidsforhold(
                fnr = kommando.fnr,
                aaregArbeidsforhold = arbeidsforhold,
            )
        }
    }
