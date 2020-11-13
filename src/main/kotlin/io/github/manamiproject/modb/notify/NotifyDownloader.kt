package io.github.manamiproject.modb.notify

import io.github.manamiproject.modb.core.config.AnimeId
import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.downloader.Downloader
import io.github.manamiproject.modb.core.httpclient.DefaultHttpClient
import io.github.manamiproject.modb.core.httpclient.HttpClient

/**
 * Downloads anime data from notify.moe
 * @since 1.0.0
 * @param config Configuration for downloading data.
 * @param httpClient To actually download the anime data.
 */
public class NotifyDownloader(
    private val config: MetaDataProviderConfig,
    private val httpClient: HttpClient = DefaultHttpClient()
) : Downloader {

    override fun download(id: AnimeId, onDeadEntry: (AnimeId) -> Unit): String {
        val response = httpClient.get(config.buildDataDownloadLink(id).toURL())

        check(response.body.isNotBlank()) { "Response body was blank for [notifyId=$id] with response code [${response.code}]" }

        return when (response.code) {
            200 -> response.body
            else -> throw IllegalStateException("Unable to determine the correct case for [notifyId=$id], [responseCode=${response.code}]")
        }
    }
}