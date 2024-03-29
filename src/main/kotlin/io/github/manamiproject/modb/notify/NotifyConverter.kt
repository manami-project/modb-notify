package io.github.manamiproject.modb.notify

import io.github.manamiproject.modb.core.json.Json
import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.converter.AnimeConverter
import io.github.manamiproject.modb.core.coroutines.ModbDispatchers.LIMITED_CPU
import io.github.manamiproject.modb.core.extensions.*
import io.github.manamiproject.modb.core.models.*
import io.github.manamiproject.modb.core.models.Anime.Status
import io.github.manamiproject.modb.core.models.Anime.Status.*
import io.github.manamiproject.modb.core.models.Anime.Type
import io.github.manamiproject.modb.core.models.Anime.Type.*
import io.github.manamiproject.modb.core.models.AnimeSeason.Season.*
import io.github.manamiproject.modb.core.models.Duration.TimeUnit.MINUTES
import kotlinx.coroutines.withContext
import java.net.URI

/**
 * The conversion requires two files. The file with all main data and a second file containing related anime.
 * IDs are always identical. If an anime doesn't provide any related anime it still has to have a file for related anime.
 * @since 1.0.0
 * @param config Configuration
 * @param relationsDir Directory containing the raw files for the related anime.
 * @throws IllegalArgumentException if the [relationsDir] doesn't exist or is not a directory.
 */
public class NotifyConverter(
    private val config: MetaDataProviderConfig = NotifyConfig,
    private val relationsDir: Directory,
) : AnimeConverter {

    init {
        require(relationsDir.directoryExists()) { "Directory for relations [$relationsDir] does not exist or is not a directory." }
    }

    override suspend fun convert(rawContent: String): Anime = withContext(LIMITED_CPU) {
        val document = Json.parseJson<NotifyDocument>(rawContent)!!

        return@withContext Anime(
            _title = extractTitle(document),
            episodes = extractEpisodes(document),
            type = extractType(document),
            picture = extractPicture(document),
            thumbnail = extractThumbnail(document),
            status = extractStatus(document),
            duration = extractDuration(document),
            animeSeason = extractAnimeSeason(document),
        ).apply {
            addSources(extractSourcesEntry(document))
            addSynonyms(extractSynonyms(document))
            addRelatedAnime(extractRelatedAnime(document))
            addTags(extractTags(document))
        }
    }

    private fun extractTitle(document: NotifyDocument) = document.title[CANONICAL] as String

    private fun extractEpisodes(document: NotifyDocument) = document.episodeCount

    private fun extractType(document: NotifyDocument): Type {
        return when(document.type.lowercase()) {
            "tv" -> TV
            "movie" -> MOVIE
            "ova" -> OVA
            "ona" -> ONA
            "special" -> SPECIAL
            "music" -> SPECIAL
            else -> throw IllegalStateException("Unknown type [${document.type}]")
        }
    }

    private fun extractPicture(document: NotifyDocument) = URI("https://media.notify.moe/images/anime/large/${document.id}${document.image.extension}")

    private fun extractThumbnail(document: NotifyDocument) = URI("https://media.notify.moe/images/anime/small/${document.id}${document.image.extension}")

    private fun extractSynonyms(document: NotifyDocument): List<Title> {
        val synonyms: List<String> = (document.title[SYNONYMS] as List<*>?)?.map { it as String } ?: emptyList()

        return document.title.filterNot { it.key == CANONICAL }
            .filterNot { it.key == SYNONYMS }
            .values
            .union(synonyms)
            .map { it as String }
    }

    private fun extractSourcesEntry(document: NotifyDocument) = listOf(config.buildAnimeLink(document.id))

    private suspend fun extractRelatedAnime(document: NotifyDocument): List<URI> = withContext(LIMITED_CPU) {
        val relationsFile = relationsDir.resolve("${document.id}.${config.fileSuffix()}")

        return@withContext if (relationsFile.regularFileExists()) {
            Json.parseJson<NotifyRelations>(relationsFile.readFile())!!.items
                ?.map { it.animeId }
                ?.map { config.buildAnimeLink(it) }
                ?: emptyList()
        } else {
            emptyList()
        }
    }

    private fun extractStatus(document: NotifyDocument): Status {
        return when(document.status) {
            "finished" -> FINISHED
            "current" -> ONGOING
            "upcoming" -> UPCOMING
            "tba" -> Status.UNKNOWN
            else -> throw IllegalStateException("Unknown status [${document.status}]")
        }
    }

    private fun extractDuration(document: NotifyDocument) = Duration(document.episodeLength, MINUTES)

    private fun extractAnimeSeason(document: NotifyDocument): AnimeSeason {
        val month = Regex("-[0-9]{2}-").findAll(document.startDate).firstOrNull()?.value?.replace("-", "")?.toInt() ?: 0
        val year = Regex("[0-9]{4}").findAll(document.startDate).firstOrNull()?.value?.toInt() ?: AnimeSeason.UNKNOWN_YEAR

        val season = when(month) {
            1, 2, 3 -> WINTER
            4, 5, 6 -> SPRING
            7, 8, 9 -> SUMMER
            10, 11, 12 -> FALL
            else -> UNDEFINED
        }

        return AnimeSeason(
            season = season,
            year = year
        )
    }

    private fun extractTags(document: NotifyDocument): List<Tag> = document.genres ?: emptyList()

    private companion object {
        private const val CANONICAL = "canonical"
        private const val SYNONYMS = "synonyms"
    }
}

private data class NotifyDocument(
    val id: String,
    val type: String,
    val title: Map<String, Any>,
    val startDate: String,
    val status: String,
    val episodeCount: Int,
    val episodeLength: Int,
    val genres: List<String>?,
    val image: NotifyImage,
)

private data class NotifyImage(
    val extension: String,
)

private data class NotifyRelations(
    val items: List<NotifyRelation>?,
)

private data class NotifyRelation(
    val animeId: String,
)