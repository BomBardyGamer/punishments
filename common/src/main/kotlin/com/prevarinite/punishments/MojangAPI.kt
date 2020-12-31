package com.prevarinite.punishments

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface MojangAPI {

    @GET("users/profiles/minecraft/{username}")
    suspend fun retrieveUUID(@Path("username") name: String): PlayerResponse
}

@Serializable
data class PlayerResponse(
    val id: MojangUUID,
    val name: String
)

@Serializable(with = MojangUUIDSerializer::class)
data class MojangUUID(
    val uuid: UUID
)

object MojangUUIDSerializer : KSerializer<UUID> {

    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: UUID) = encoder.encodeString(value.toString())
}

fun UUID.toMojang() = MojangUUID(this)

fun mojangModule() = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.mojang.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MojangAPI::class.java)
    }
}