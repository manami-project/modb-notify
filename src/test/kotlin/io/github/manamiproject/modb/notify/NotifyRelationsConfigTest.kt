package io.github.manamiproject.modb.notify

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URL

internal class NotifyRelationsConfigTest {

    @Test
    fun `isTestContext is false`() {
        // when
        val result = NotifyRelationsConfig.isTestContext()

        // then
        assertThat(result).isFalse()
    }

    @Test
    fun `hostname must be equal to NotifyConfig`() {
        // when
        val result = NotifyRelationsConfig.hostname()

        // then
        assertThat(result).isEqualTo(NotifyConfig.hostname())
    }

    @Test
    fun `anime link URL is the same as for NotifyConfig`() {
        // given
        val id = "1376"

        // when
        val result = NotifyRelationsConfig.buildAnimeLinkUrl(id)

        // then
        assertThat(result).isEqualTo(NotifyConfig.buildAnimeLinkUrl(id))
    }

    @Test
    fun `build data download URL correctly`() {
        // given
        val id = "lkj452"

        // when
        val result = NotifyRelationsConfig.buildDataDownloadUrl(id)

        // then
        assertThat(result).isEqualTo(URL("https://notify.moe/api/animerelations/$id"))
    }

    @Test
    fun `file suffix must be json`() {
        // when
        val result = NotifyRelationsConfig.fileSuffix()

        // then
        assertThat(result).isEqualTo("json")
    }
}
