package com.example.kuit6_notification.util

object Constants {
    // Notification Channel
    const val NOTIFICATION_CHANNEL_ID = "timer_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Timer Notifications"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications for timer events"

    // Notification IDs
    const val TIMER_NOTIFICATION_ID = 1001
    const val TIMER_COMPLETED_NOTIFICATION_ID = 1002

    // Service
    const val TIMER_SERVICE_ID = 1001

    // Intent Actions
    const val ACTION_START_TIMER = "com.example.kuit6_notification.ACTION_START_TIMER"
    const val ACTION_PAUSE_TIMER = "com.example.kuit6_notification.ACTION_PAUSE_TIMER"
    const val ACTION_STOP_TIMER = "com.example.kuit6_notification.ACTION_STOP_TIMER"
    const val ACTION_RESTART_TIMER = "com.example.kuit6_notification.ACTION_RESTART_TIMER"

    // Timer defaults
    const val DEFAULT_TIMER_MILLIS = 60000L // 1 minute
}
