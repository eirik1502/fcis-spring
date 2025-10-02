package com.example.demo.skall

import com.example.demo.kjerne.Plan
import com.example.demo.kjerne.tilPenTekst
import org.springframework.stereotype.Component

@Component
class PlanUtfører {
    fun utførPlan(plan: Plan) {
        println("Utfører plan: \n${plan.tilPenTekst()}")
    }
}
