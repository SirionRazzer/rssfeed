package eu.soczilla.rssfeed

import com.ouattararomuald.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ouattararomuald.syndication.Rss
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

fun main(args: Array<String>) = runBlocking {
    val client = OkHttpClient.Builder()
        .build()

    val syndicationReader = Syndication(
        url = "https://www.root.cz/rss/zpravicky/",
        callFactory = CoroutineCallAdapterFactory(),
        httpClient = client
    )

    val reader = syndicationReader.create(RssReader::class.java)
    println(reader.readRss().await().channel.items?.first()?.title)
}

interface RssReader {
    @Rss(returnClass = CustomRssFeed::class)
    fun readRss(): Deferred<CustomRssFeed>
}

