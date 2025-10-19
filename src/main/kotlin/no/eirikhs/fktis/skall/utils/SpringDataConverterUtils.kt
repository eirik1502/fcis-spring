package no.eirikhs.fktis.skall.utils

import org.postgresql.util.PGobject
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@ReadingConverter
abstract class JsonbReadingConverter<T : Any> : Converter<PGobject, T> {
    abstract fun convertValue(value: String): T

    override fun convert(source: PGobject): T =
        try {
            source.value?.let {
                convertValue(it)
            } ?: throw IllegalStateException("PGobject value is null")
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}

@WritingConverter
abstract class JsonbWritingConverter<T : Any> : Converter<T, PGobject> {
    abstract fun convertValue(value: T): String

    override fun convert(source: T): PGobject {
        try {
            return PGobject().apply {
                type = "jsonb"
                value = convertValue(source)
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
