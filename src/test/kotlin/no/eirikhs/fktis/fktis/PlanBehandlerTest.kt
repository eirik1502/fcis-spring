package no.eirikhs.fktis.fktis

import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.eirikhs.fktis.fktis.kjerne.Effekt
import no.eirikhs.fktis.fktis.kjerne.Kommando
import no.eirikhs.fktis.fktis.kjerne.UtførKommandoSteg
import no.eirikhs.fktis.fktis.kjerne.byggPlan
import no.eirikhs.fktis.fktis.skall.EffektDistributør
import no.eirikhs.fktis.fktis.skall.KommandoPlanleggerDistributør
import no.eirikhs.fktis.fktis.skall.PlanBehandler
import no.eirikhs.fktis.fktis.skall.hjelpere.NoOpTransaksjonBehandler
import org.junit.jupiter.api.Test

class PlanBehandlerTest {
    val effektDistributør: EffektDistributør = mockk<EffektDistributør>()

    val kommandoPlanleggerDistributør: KommandoPlanleggerDistributør = mockk<KommandoPlanleggerDistributør>()

    val planBehandler: PlanBehandler =
        PlanBehandler(
            effektDistributør = effektDistributør,
            kommandoPlanleggerDistributør = kommandoPlanleggerDistributør,
            transaksjonBehandler = NoOpTransaksjonBehandler,
        )

    @Test
    fun `burde utføre enkel plan`() {
        val plan =
            byggPlan {
                +TestEffekt()
            }

        planBehandler.utfør(plan)

        verify { effektDistributør.utfør(TestEffekt()) }
    }

    @Test
    fun `burde utføre plan i plan`() {
        val plan =
            byggPlan {
                +byggPlan {
                    +TestEffekt()
                }
            }

        planBehandler.utfør(plan)

        verify { effektDistributør.utfør(TestEffekt()) }
    }

    @Test
    fun `burde ekspandere plan`() {
        val plan =
            byggPlan {
                +TestKommando()
            }

        every { kommandoPlanleggerDistributør.planlegg(TestKommando()) } returns
            byggPlan {
                +TestEffekt()
            }
        planBehandler.utfør(plan)

        verify { effektDistributør.utfør(TestEffekt()) }
    }
}

data class TestEffekt(
    val data: String = "data",
) : Effekt

data class TestKommando(
    val data: String = "data",
) : Kommando
