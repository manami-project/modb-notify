package io.github.manamiproject.modb.notify

import io.github.manamiproject.modb.core.extensions.writeToFile
import io.github.manamiproject.modb.test.testResource
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    val downloader = NotifyDownloader(NotifyConfig)
    val relationsDownloader = NotifyDownloader(NotifyRelationsConfig)

    downloader.download("w1khcFmig").writeToFile(resourceFile("file_converter_tests/anime_season/1989.json"))
    downloader.download("5Vd6nwsiR").writeToFile(resourceFile("file_converter_tests/anime_season/fall.json"))
    downloader.download("_SZLtKimR").writeToFile(resourceFile("file_converter_tests/anime_season/no_year.json"))
    downloader.download("5K5MBjvmR").writeToFile(resourceFile("file_converter_tests/anime_season/spring.json"))
    downloader.download("TNo6W6vmR").writeToFile(resourceFile("file_converter_tests/anime_season/summer.json"))
    downloader.download("_SZLtKimR").writeToFile(resourceFile("file_converter_tests/anime_season/undefined.json"))
    downloader.download("ohndcHvmg").writeToFile(resourceFile("file_converter_tests/anime_season/winter.json"))

    downloader.download("BEHHhFiiR").writeToFile(resourceFile("file_converter_tests/duration/0.json"))
    downloader.download("gLmp5FimR").writeToFile(resourceFile("file_converter_tests/duration/24.json"))
    downloader.download("tMrEpKiig").writeToFile(resourceFile("file_converter_tests/duration/120.json"))

    downloader.download("d_0bcKmmR").writeToFile(resourceFile("file_converter_tests/episodes/39.json"))

    downloader.download("IkCdhKimR").writeToFile(resourceFile("file_converter_tests/picture_and_thumbnail/picture_and_thumbnail_available.json"))

    relationsDownloader.download("--eZhFiig").writeToFile(resourceFile("file_converter_tests/related_anime/items_in_relations_file_is_null/relations.json"))
    downloader.download("--eZhFiig").writeToFile(resourceFile("file_converter_tests/related_anime/items_in_relations_file_is_null/anime.json"))
    relationsDownloader.download("uLs5tKiig").writeToFile(resourceFile("file_converter_tests/related_anime/multiple_relations/relations.json"))
    downloader.download("uLs5tKiig").writeToFile(resourceFile("file_converter_tests/related_anime/multiple_relations/anime.json"))
    relationsDownloader.download("5Vd6nwsiR").writeToFile(resourceFile("file_converter_tests/related_anime/no_relations/relations.json"))
    downloader.download("5Vd6nwsiR").writeToFile(resourceFile("file_converter_tests/related_anime/no_relations/anime.json"))
    downloader.download("5Vd6nwsiR").writeToFile(resourceFile("file_converter_tests/related_anime/no_relations_file/anime.json"))

    downloader.download("0-A-5Fimg").writeToFile(resourceFile("file_converter_tests/sources/0-A-5Fimg.json"))

    downloader.download("IkCdhKimR").writeToFile(resourceFile("file_converter_tests/status/current.json"))
    downloader.download("Ml2V2KiiR").writeToFile(resourceFile("file_converter_tests/status/finished.json"))
    downloader.download("FL0V2Kmmg").writeToFile(resourceFile("file_converter_tests/status/not_mapped.json"))
    downloader.download("BEHHhFiiR").writeToFile(resourceFile("file_converter_tests/status/tba.json"))
    downloader.download("Y4LniqnmR").writeToFile(resourceFile("file_converter_tests/status/upcoming.json"))

    downloader.download("DtwM2Kmig").writeToFile(resourceFile("file_converter_tests/synonyms/combine_non_canonical_title_and_synonyms.json"))
    downloader.download("rTgwpFmmg").writeToFile(resourceFile("file_converter_tests/synonyms/synonyms_is_null.json"))

    downloader.download("rTgwpFmmg").writeToFile(resourceFile("file_converter_tests/tags/genres_is_null.json"))
    downloader.download("q9yku_3ig").writeToFile(resourceFile("file_converter_tests/tags/tags_from_genres.json"))

    downloader.download("MkGrtKmmR").writeToFile(resourceFile("file_converter_tests/title/special_chars.json"))

    downloader.download("FL0V2Kmmg").writeToFile(resourceFile("file_converter_tests/type/movie.json"))
    downloader.download("Ff1bpKmmR").writeToFile(resourceFile("file_converter_tests/type/music.json"))
    downloader.download("OamVhFmmR").writeToFile(resourceFile("file_converter_tests/type/ona.json"))
    downloader.download("MYsOvq7ig").writeToFile(resourceFile("file_converter_tests/type/ova.json"))
    downloader.download("a8RVhKmmR").writeToFile(resourceFile("file_converter_tests/type/special.json"))
    downloader.download("Ml2V2KiiR").writeToFile(resourceFile("file_converter_tests/type/tv.json"))
    downloader.download("Ml2V2KiiR").writeToFile(resourceFile("file_converter_tests/type/unknown_type.json"))
}

private fun resourceFile(file: String): Path {
    return Paths.get(
        testResource(file).toAbsolutePath()
            .toString()
            .replace("/build/resources/test/", "/src/test/resources/")
    )
}