/*
This file is part of FeedWatcher.

FeedWatcher is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

FeedWatcher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with FeedWatcher. If not, see <https://www.gnu.org/licenses/>.
Copyright 2019 Zouroboros
 */
package me.murks.feedwatcher

import me.murks.feedwatcher.data.AddResultsAndMarkFeeds
import me.murks.feedwatcher.model.Feed
import me.murks.feedwatcher.model.Query
import me.murks.feedwatcher.model.Result
import me.murks.feedwatcher.tasks.ActionTask
import me.murks.feedwatcher.tasks.ErrorHandlingTaskListener
import java.io.Closeable
import java.lang.Exception
import java.util.*

/**
 * @author zouroboros
 */
class FeedWatcherApp(val environment: Environment) {

    fun queries(): List<Query> {
        val queries = environment.dataStore.getQueries()
        return queries
    }

    fun feeds(): List<Feed> {
        val feeds = environment.dataStore.getFeeds()
        return feeds
    }


    fun results(listener: ErrorHandlingTaskListener<List<Result>, List<Result>, Exception>): ActionTask<List<Result>>
            = ActionTask({ environment.dataStore.getResults()}, listener)

    fun updateQuery(query: Query) {
        environment.dataStore.updateQuery(query)
    }

    fun addQuery(query: Query) {
        environment.dataStore.addQuery(query)
    }

    fun addFeed(feed: Feed) {
        environment.dataStore.addFeed(feed)
    }

    fun delete(feed: Feed) {
        environment.dataStore.delete(feed)
    }

    fun query(id: Long): Query {
        return environment.dataStore.query(id)
    }

    fun result(id: Long): Result {
        return environment.dataStore.result(id)
    }

    fun delete(result: Result) {
        environment.dataStore.delete(result)
    }

    fun delete(query: Query) {
        environment.dataStore.delete(query)
    }

    /**
     * Reschedules the background scanning according to the current settings
     */
    fun rescheduleJobs() {
        environment.jobs.rescheduleJobs(environment.settings)
    }

    /**
     * Sends the scan results to the app
     */
    fun scanResults(results: List<Result>) {
        if(results.isNotEmpty() && environment.settings.showNotifcations) {
            environment.notifications.newResults(results, environment.settings)
        }
        environment.dataStore.submit(AddResultsAndMarkFeeds(results, Date()))
    }
}