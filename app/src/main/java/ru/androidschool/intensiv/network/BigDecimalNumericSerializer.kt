@file:Suppress("MaxLineLength")

package ru.androidschool.intensiv.network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.jsonPrimitive
import java.math.BigDecimal

/**
 * Взято с [офицального теста](https://github.com/Kotlin/kotlinx.serialization/blob/master/formats/json-tests/jvmTest/src/kotlinx/serialization/BigDecimalTest.kt)
 * */
@ExperimentalSerializationApi
class BigDecimalNumericSerializer : KSerializer<BigDecimal> {

    override val descriptor = PrimitiveSerialDescriptor(
        serialName = "java.math.BigDecimal",
        kind = PrimitiveKind.DOUBLE
    )

    override fun deserialize(decoder: Decoder): BigDecimal {
        return if (decoder is JsonDecoder) {
            BigDecimal(decoder.decodeJsonElement().jsonPrimitive.content)
        } else {
            BigDecimal(decoder.decodeString())
        }
    }

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        val bdString = value.toPlainString()
        if (encoder is JsonEncoder) {
            encoder.encodeJsonElement(JsonUnquotedLiteral(bdString))
        } else {
            encoder.encodeString(bdString)
        }
    }
}
