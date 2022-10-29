package io.github.manamiproject.modb.notify

import io.github.manamiproject.modb.core.extensions.writeToFileSuspendable
import io.github.manamiproject.modb.test.testResource
import kotlinx.coroutines.runBlocking
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    val downloader = NotifyDownloader(NotifyConfig)
    val relationsDownloader = NotifyDownloader(NotifyRelationsConfig)
    
    runBlocking { 
        downloader.downloadSuspendable("w1khcFmig").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/1989.json"))
        downloader.downloadSuspendable("5Vd6nwsiR").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/fall.json"))
        downloader.downloadSuspendable("_SZLtKimR").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/no_year.json"))
        downloader.downloadSuspendable("5K5MBjvmR").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/spring.json"))
        downloader.downloadSuspendable("TNo6W6vmR").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/summer.json"))
        downloader.downloadSuspendable("_SZLtKimR").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/undefined.json"))
        downloader.downloadSuspendable("ohndcHvmg").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/winter.json"))
    
        downloader.downloadSuspendable("BEHHhFiiR").writeToFileSuspendable(resourceFile("file_converter_tests/duration/0.json"))
        downloader.downloadSuspendable("gLmp5FimR").writeToFileSuspendable(resourceFile("file_converter_tests/duration/24.json"))
        downloader.downloadSuspendable("tMrEpKiig").writeToFileSuspendable(resourceFile("file_converter_tests/duration/120.json"))
    
        downloader.downloadSuspendable("d_0bcKmmR").writeToFileSuspendable(resourceFile("file_converter_tests/episodes/39.json"))
    
        downloader.downloadSuspendable("IkCdhKimR").writeToFileSuspendable(resourceFile("file_converter_tests/picture_and_thumbnail/picture_and_thumbnail_available.json"))
    
        relationsDownloader.downloadSuspendable("--eZhFiig").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/items_in_relations_file_is_null/relations.json"))
        downloader.downloadSuspendable("--eZhFiig").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/items_in_relations_file_is_null/anime.json"))
        relationsDownloader.downloadSuspendable("uLs5tKiig").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/multiple_relations/relations.json"))
        downloader.downloadSuspendable("uLs5tKiig").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/multiple_relations/anime.json"))
        relationsDownloader.downloadSuspendable("5Vd6nwsiR").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/no_relations/relations.json"))
        downloader.downloadSuspendable("5Vd6nwsiR").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/no_relations/anime.json"))
        downloader.downloadSuspendable("5Vd6nwsiR").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/no_relations_file/anime.json"))
    
        downloader.downloadSuspendable("0-A-5Fimg").writeToFileSuspendable(resourceFile("file_converter_tests/sources/0-A-5Fimg.json"))
    
        downloader.downloadSuspendable("Y2eEDZzMR").writeToFileSuspendable(resourceFile("file_converter_tests/status/current.json"))
        downloader.downloadSuspendable("Ml2V2KiiR").writeToFileSuspendable(resourceFile("file_converter_tests/status/finished.json"))
        downloader.downloadSuspendable("OkibM4KGg").writeToFileSuspendable(resourceFile("file_converter_tests/status/tba.json"))
        downloader.downloadSuspendable("rBaaLj2Wg").writeToFileSuspendable(resourceFile("file_converter_tests/status/upcoming.json"))
    
        downloader.downloadSuspendable("DtwM2Kmig").writeToFileSuspendable(resourceFile("file_converter_tests/synonyms/combine_non_canonical_title_and_synonyms.json"))
        downloader.downloadSuspendable("rTgwpFmmg").writeToFileSuspendable(resourceFile("file_converter_tests/synonyms/synonyms_is_null.json"))
    
        downloader.downloadSuspendable("rTgwpFmmg").writeToFileSuspendable(resourceFile("file_converter_tests/tags/genres_is_null.json"))
        downloader.downloadSuspendable("q9yku_3ig").writeToFileSuspendable(resourceFile("file_converter_tests/tags/tags_from_genres.json"))
    
        downloader.downloadSuspendable("MkGrtKmmR").writeToFileSuspendable(resourceFile("file_converter_tests/title/special_chars.json"))
    
        downloader.downloadSuspendable("FL0V2Kmmg").writeToFileSuspendable(resourceFile("file_converter_tests/type/movie.json"))
        downloader.downloadSuspendable("Ff1bpKmmR").writeToFileSuspendable(resourceFile("file_converter_tests/type/music.json"))
        downloader.downloadSuspendable("OamVhFmmR").writeToFileSuspendable(resourceFile("file_converter_tests/type/ona.json"))
        downloader.downloadSuspendable("MYsOvq7ig").writeToFileSuspendable(resourceFile("file_converter_tests/type/ova.json"))
        downloader.downloadSuspendable("a8RVhKmmR").writeToFileSuspendable(resourceFile("file_converter_tests/type/special.json"))
        downloader.downloadSuspendable("Ml2V2KiiR").writeToFileSuspendable(resourceFile("file_converter_tests/type/tv.json"))
    }
}

private fun resourceFile(file: String): Path {
    return Paths.get(
        testResource(file).toAbsolutePath()
            .toString()
            .replace("/build/resources/test/", "/src/test/resources/")
    )
}