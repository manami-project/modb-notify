package io.github.manamiproject.modb.notify

import io.github.manamiproject.modb.core.config.FileSuffix
import io.github.manamiproject.modb.core.config.Hostname
import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import java.net.URL

/**
 * Configuration for downloading anime data from notify.moe
 * @since 1.0.0
 */
object NotifyConfig : MetaDataProviderConfig {

    override fun buildDataDownloadUrl(id: String): URL = URL("https://${hostname()}/api/anime/$id")

    override fun hostname(): Hostname = "notify.moe"

    override fun fileSuffix(): FileSuffix = "json"
}
