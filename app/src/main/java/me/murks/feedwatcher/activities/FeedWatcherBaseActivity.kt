package me.murks.feedwatcher.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.murks.feedwatcher.FeedWatcherApp

/**
 * Base activity for all activities of the feed watcher app.
 * Provides common fields and methods
 * @author zouroboros
 */
abstract class FeedWatcherBaseActivity(): AppCompatActivity() {
    lateinit var app: FeedWatcherApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = FeedWatcherApp(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        app.close()
    }
}