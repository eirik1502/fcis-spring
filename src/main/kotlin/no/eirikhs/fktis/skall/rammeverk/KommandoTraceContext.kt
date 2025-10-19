package no.eirikhs.fktis.skall.rammeverk

import java.util.*

object KommandoTraceContext {
    private val traceIdHolder = ThreadLocal<String?>()

    fun getTraceId(): String {
        val traceId = traceIdHolder.get()
        if (traceId == null) {
            traceIdHolder.set(UUID.randomUUID().toString())
        }
        return traceIdHolder.get()!!
    }

    fun clear() {
        traceIdHolder.remove()
    }
}
