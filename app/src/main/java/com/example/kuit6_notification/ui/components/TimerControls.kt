package com.example.kuit6_notification.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kuit6_notification.model.TimerState
import com.example.kuit6_notification.ui.theme.Kuit6_NotificationTheme

@Composable
fun TimerControls(
    timerState: TimerState,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        when (timerState) {
            is TimerState.Idle -> {
                Button(
                    onClick = onStart,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "시작")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("시작")
                }
            }

            is TimerState.Running -> {
                Button(
                    onClick = onPause,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("일시정지")
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "리셋")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("리셋")
                }
            }

            is TimerState.Paused -> {
                Button(
                    onClick = onStart,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "재개")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("재개")
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "리셋")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("리셋")
                }
            }

            is TimerState.Completed -> {
                Button(
                    onClick = onReset,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "다시 시작")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("다시 시작")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerControlsIdlePreview() {
    Kuit6_NotificationTheme {
        TimerControls(
            timerState = TimerState.Idle,
            onStart = {},
            onPause = {},
            onReset = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerControlsRunningPreview() {
    Kuit6_NotificationTheme {
        TimerControls(
            timerState = TimerState.Running(45000L),
            onStart = {},
            onPause = {},
            onReset = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerControlsPausedPreview() {
    Kuit6_NotificationTheme {
        TimerControls(
            timerState = TimerState.Paused(30000L),
            onStart = {},
            onPause = {},
            onReset = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerControlsCompletedPreview() {
    Kuit6_NotificationTheme {
        TimerControls(
            timerState = TimerState.Completed,
            onStart = {},
            onPause = {},
            onReset = {}
        )
    }
}
