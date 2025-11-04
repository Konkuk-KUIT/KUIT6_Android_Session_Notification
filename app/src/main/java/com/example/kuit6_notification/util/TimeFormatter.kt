package com.example.kuit6_notification.util

import android.annotation.SuppressLint

object TimeFormatter {
    @SuppressLint("DefaultLocale")
    fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun toMillis(
        hours: Int,
        minutes: Int,
        seconds: Int
    ): Long {
        return ((hours * 3600L) + (minutes * 60L) + seconds) * 1000L
    }

}
