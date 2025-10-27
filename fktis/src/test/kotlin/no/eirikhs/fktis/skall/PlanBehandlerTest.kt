package no.eirikhs.fktis.skall

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.eirikhs.fktis.kjerne.byggPlan
import no.eirikhs.fktis.skall.hjelpere.NoOpTransaksjonBehandler
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
) : no.eirikhs.fktis.kjerne.Effekt

data class TestKommando(
    val data: String = "data",
) : no.eirikhs.fktis.kjerne.Kommando
