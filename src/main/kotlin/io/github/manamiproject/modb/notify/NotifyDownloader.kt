package io.github.manamiproject.modb.notify

import io.github.manamiproject.modb.core.config.AnimeId
import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.coroutines.ModbDispatchers.LIMITED_NETWORK
import io.github.manamiproject.modb.core.downloader.Downloader
import io.github.manamiproject.modb.core.extensions.EMPTY
import io.github.manamiproject.modb.core.httpclient.DefaultHttpClient
import io.github.manamiproject.modb.core.httpclient.HttpClient
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Downloads anime data from notify.moe
 * @since 1.0.0
 * @param config Configuration for downloading data.
 * @param httpClient To actually download the anime data.
 */
public class NotifyDownloader(
    private val config: MetaDataProviderConfig,
    private val httpClient: HttpClient = DefaultHttpClient(isTestContext = config.isTestContext()),
) : Downloader {

    @Deprecated("Use coroutines",
        ReplaceWith("runBlocking { downloadSuspendable(id, onDeadEntry) }", "kotlinx.coroutines.runBlocking")
    )
    override fun download(id: AnimeId, onDeadEntry: (AnimeId) -> Unit): String = runBlocking {
        downloadSuspendable(id, onDeadEntry)
    }

    override suspend fun downloadSuspendable(id: AnimeId, onDeadEntry: (AnimeId) -> Unit): String = withContext(LIMITED_NETWORK) {
        val response = httpClient.getSuspedable(config.buildDataDownloadLink(id).toURL())

        check(response.body.isNotBlank()) { "Response body was blank for [notifyId=$id] with response code [${response.code}]" }

        return@withContext when (response.code) {
            200 -> response.body
            404 -> {
                onDeadEntry.invoke(id)
                EMPTY
            }
            else -> throw IllegalStateException("Unable to determine the correct case for [notifyId=$id], [responseCode=${response.code}]")
        }
    }
}