package com.example.demo.skall.rammeverk

import com.example.demo.kjerne.Plan
import org.springframework.stereotype.Component

@Component
class PlanUtfører(
    private val effektUtførerRegister: EffektUtførerRegister,
) {
    fun utførPlan(plan: Plan) {
        for (effekt in plan.effekter) {
            val effektUtfører = effektUtførerRegister.finnEffektUtfører(effekt)
            effektUtfører.utfør(effekt)
        }
    }
}
