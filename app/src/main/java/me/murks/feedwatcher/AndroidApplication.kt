package me.murks.feedwatcher

import android.app.Application
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import me.murks.feedwatcher.tasks.FilterFeedsJob
import java.lang.RuntimeException


/**
 * @author zouroboros
 */
class AndroidApplication(): Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        scheduleJobs()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.result_notification_channel_name)
            val description = getString(R.string.result_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            channel.importance = NotificationManager.IMPORTANCE_LOW
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    private fun scheduleJobs() {
        val jobScheduler = getSystemService(JobScheduler::class.java)
        if(jobScheduler.allPendingJobs.isEmpty()) {
            val jobBuilder = JobInfo.Builder(1, ComponentName(this, FilterFeedsJob::class.java))

            val period = 1000L * 60 * 15

            jobBuilder.setPeriodic(period)
                    .setPersisted(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            val result = jobScheduler.schedule(jobBuilder.build())
            if(result != JobScheduler.RESULT_SUCCESS) {
                throw RuntimeException("Couldn't schedule job!")
            }
            // TODO schedule jobs on boot
            // TODO only schedule job when at least one query is set up
        }
    }

    companion object {
        const val CHANNEL_ID = "result.notification.channel"
    }
}