package io.github.manamiproject.modb.notify

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URL

internal class NotifyConfigTest {

    @Test
    fun `isTestContext is false`() {
        // when
        val result = NotifyConfig.isTestContext()

        // then
        assertThat(result).isFalse()
    }

    @Test
    fun `hostname must be correct`() {
        // when
        val result = NotifyConfig.hostname()

        // then
        assertThat(result).isEqualTo("notify.moe")
    }

    @Test
    fun `build anime link URL correctly`() {
        // given
        val id = "0-A-5Fimg"

        // when
        val result = NotifyConfig.buildAnimeLinkUrl(id)

        // then
        assertThat(result).isEqualTo(URL("https://${NotifyConfig.hostname()}/anime/$id"))
    }

    @Test
    fun `build data download URL correctly`() {
        // given
        val id = "1535"

        // when
        val result = NotifyConfig.buildDataDownloadUrl(id)

        // then
        assertThat(result).isEqualTo(URL("https://${NotifyConfig.hostname()}/api/anime/$id"))
    }

    @Test
    fun `file suffix must be html`() {
        // when
        val result = NotifyConfig.fileSuffix()

        // then
        assertThat(result).isEqualTo("json")
    }
}
