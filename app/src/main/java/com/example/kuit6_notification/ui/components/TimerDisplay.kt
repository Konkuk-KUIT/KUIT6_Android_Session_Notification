package com.example.kuit6_notification.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kuit6_notification.model.TimerState
import com.example.kuit6_notification.ui.theme.Kuit6_NotificationTheme
import com.example.kuit6_notification.util.TimeFormatter

@Composable
fun TimerDisplay(
    timerState: TimerState,
    initialMillis: Long,
    modifier: Modifier = Modifier
) {
    val displayTime = when (timerState) {
        is TimerState.Idle -> TimeFormatter.formatTime(initialMillis)
        is TimerState.Running -> TimeFormatter.formatTime(timerState.remainingMillis)
        is TimerState.Paused -> TimeFormatter.formatTime(timerState.remainingMillis)
        is TimerState.Completed -> "00:00:00"
    }

    val targetProgress = when (timerState) {
        is TimerState.Running -> {
            if (initialMillis > 0) timerState.remainingMillis.toFloat() / initialMillis.toFloat() else 0f
        }
        is TimerState.Paused -> {
            if (initialMillis > 0) timerState.remainingMillis.toFloat() / initialMillis.toFloat() else 0f
        }
        else -> 1f
    }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 100),
        label = "progress"
    )

    val statusText = when (timerState) {
        is TimerState.Idle -> "준비"
        is TimerState.Running -> "실행 중"
        is TimerState.Paused -> "일시 정지"
        is TimerState.Completed -> "완료"
    }

    val textColor = when (timerState) {
        is TimerState.Running -> MaterialTheme.colorScheme.primary
        is TimerState.Completed -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(280.dp)
        ) {
            if (timerState is TimerState.Running || timerState is TimerState.Paused) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(280.dp),
                    strokeWidth = 8.dp,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            AnimatedContent(
                targetState = displayTime,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150)) togetherWith
                            fadeOut(animationSpec = tween(150))
                },
                label = "time_text"
            ) { time ->
                Text(
                    text = time,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = statusText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerDisplayIdlePreview() {
    Kuit6_NotificationTheme {
        TimerDisplay(
            timerState = TimerState.Idle,
            initialMillis = 60000L,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerDisplayRunningPreview() {
    Kuit6_NotificationTheme {
        TimerDisplay(
            timerState = TimerState.Running(45000L),
            initialMillis = 60000L,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerDisplayCompletedPreview() {
    Kuit6_NotificationTheme {
        TimerDisplay(
            timerState = TimerState.Completed,
            initialMillis = 60000L,
            modifier = Modifier.padding(16.dp)
        )
    }
}
