package com.example.kuit6_notification.model

sealed class TimerState {
    data object Idle : TimerState()
    data class Running(val remainingMillis: Long) : TimerState()
    data class Paused(val remainingMillis: Long) : TimerState()
    data object Completed : TimerState()
}
