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

    // ATOM: https://www.cnews.cz/feed/
    // https://www.root.cz/rss/zpravicky/
    // https://diit.cz/rss.xml
    val syndicationReader = Syndication(
        url = "https://diit.cz/rss.xml",
        callFactory = CoroutineCallAdapterFactory(),
        httpClient = client
    )

    val reader = syndicationReader.create(RssReader::class.java)
    val items = reader.readRss().await().channel.items
    val titles = mutableListOf<String>()
    items?.forEach { item ->
        item.title?.let { titles.add(it) }
    }

    println(titles)
}

interface RssReader {
    @Rss(returnClass = CustomRssFeed::class)
    fun readRss(): Deferred<CustomRssFeed>
}

