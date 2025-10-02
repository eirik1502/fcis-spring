package com.example.demo.skall

import com.example.demo.core.Plan
import org.springframework.stereotype.Component

@Component
class PlanUtfører {
    fun utførPlan(plan: Plan) {
        println("Utfører plan: $plan")
    }
}