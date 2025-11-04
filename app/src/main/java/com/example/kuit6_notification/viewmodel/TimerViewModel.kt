package com.example.kuit6_notification.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kuit6_notification.model.TimerState
import com.example.kuit6_notification.util.TimeFormatter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val _timerState = MutableStateFlow<TimerState>(TimerState.Idle)
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _hours = MutableStateFlow(0)
    val hours: StateFlow<Int> = _hours.asStateFlow()

    private val _minutes = MutableStateFlow(0)
    val minutes: StateFlow<Int> = _minutes.asStateFlow()

    private val _seconds = MutableStateFlow(10)  // 기본값 10초
    val seconds: StateFlow<Int> = _seconds.asStateFlow()

    private var timerJob: Job? = null
    private var initialMillis: Long = 60000L

    fun setHours(value: Int) {
        if (_timerState.value is TimerState.Idle) {
            _hours.value = value
        }
    }

    fun setMinutes(value: Int) {
        if (_timerState.value is TimerState.Idle) {
            _minutes.value = value
        }
    }

    fun setSeconds(value: Int) {
        if (_timerState.value is TimerState.Idle) {
            _seconds.value = value
        }
    }

    fun startTimer() {
        when (val currentState = _timerState.value) {
            is TimerState.Idle -> {
                initialMillis = TimeFormatter.toMillis(_hours.value, _minutes.value, _seconds.value)
                if (initialMillis > 0) {
                    _timerState.value = TimerState.Running(initialMillis)
                    startCountdown(initialMillis)
                }
            }

            is TimerState.Paused -> {
                _timerState.value = TimerState.Running(currentState.remainingMillis)
                startCountdown(currentState.remainingMillis)
            }

            else -> {}
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        val currentState = _timerState.value
        if (currentState is TimerState.Running) {
            _timerState.value = TimerState.Paused(currentState.remainingMillis)
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _timerState.value = TimerState.Idle
    }

    private fun startCountdown(startMillis: Long) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var remainingMillis = startMillis
            val updateInterval = 50L

            while (remainingMillis > 0) {
                delay(updateInterval)
                remainingMillis -= updateInterval

                if (remainingMillis <= 0) {
                    _timerState.value = TimerState.Completed
                } else {
                    _timerState.value = TimerState.Running(remainingMillis)
                }
            }
        }
    }

    fun getInitialMillis(): Long {
        return when (_timerState.value) {
            is TimerState.Idle -> TimeFormatter.toMillis(
                _hours.value,
                _minutes.value,
                _seconds.value
            )

            is TimerState.Running -> initialMillis
            is TimerState.Paused -> initialMillis
            is TimerState.Completed -> initialMillis
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
