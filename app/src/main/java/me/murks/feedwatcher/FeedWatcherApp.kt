package me.murks.feedwatcher

import android.content.Context
import me.murks.feedwatcher.data.DataStore
import me.murks.feedwatcher.model.Feed
import me.murks.feedwatcher.model.Query
import me.murks.feedwatcher.model.Result
import java.util.*

/**
 * @author zouroboros
 * @date 8/13/18.
 */
class FeedWatcherApp(private val context: Context) {

    val dataStore = DataStore(context)

    fun queries(): List<Query> {
            val queries = dataStore.getQueries()
            return queries
        }

    fun feeds(): List<Feed> {
        val feeds = dataStore.getFeeds()
        return feeds
    }


    fun results(): List<Result> {
        val results = dataStore.getResults()
        return results
    }

    fun updateQuery(query: Query) {
        dataStore.updateQuery(query)
    }

    fun addQuery(query: Query) {
        dataStore.addQuery(query)
    }

    fun addFeed(feed: Feed) {
        dataStore.addFeed(feed)
    }

    fun updateFeed(feed: Feed) {
        dataStore.updateFeed(feed)
    }

    fun addResult(result: Result) {
        dataStore.addResultAndUpdateFeed(result, Feed(result.feed.url, Date()))
    }

    fun close() {
        dataStore.close()
    }
}