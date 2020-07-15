package io.github.manamiproject.modb.notify

import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import java.net.URL

/**
 * Configuration for downloading related anime from notify.moe.
 * @since 1.0.0
 */
object NotifyRelationsConfig : MetaDataProviderConfig by NotifyConfig {

    override fun buildDataDownloadUrl(id: String): URL = URL("https://notify.moe/api/animerelations/$id")
}
