package ru.androidschool.intensiv.network.deserializers

import androidx.annotation.Keep
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.model.movies.MoviesCollection

internal object MoviesCollectionDeserializer : KSerializer<MoviesCollection> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Movie")

    override fun serialize(encoder: Encoder, value: MoviesCollection) {
        throw UnsupportedOperationException(
            "Serialization of MoviesCollectionDeserializer is not supported"
        )
    }

    override fun deserialize(decoder: Decoder): MoviesCollection {
        require(decoder is JsonDecoder)
        val rootElement = decoder.decodeJsonElement()
        val remote = decoder.json.decodeFromJsonElement<RemoteMoviesCollection>(rootElement)

        return MoviesCollection(
            id = remote.id,
            name = remote.name,
            backdropPath = "${BuildConfig.POSTER_PATH_PREFIX}${remote.backdropPath}",
            posterPath = "${BuildConfig.POSTER_PATH_PREFIX}${remote.posterPath}",
        )
    }

    @Keep
    @Serializable
    private class RemoteMoviesCollection(
        val id: Int,
        val name: String,

        @SerialName("poster_path")
        val posterPath: String,

        @SerialName("backdrop_path")
        val backdropPath: String,
    )
}
