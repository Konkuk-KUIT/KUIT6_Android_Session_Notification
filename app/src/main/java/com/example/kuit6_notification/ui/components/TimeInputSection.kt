package com.example.kuit6_notification.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kuit6_notification.ui.theme.Kuit6_NotificationTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimeInputSection(
    hours: Int,
    minutes: Int,
    seconds: Int,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    // 총 초 계산
    val totalSeconds = hours * 3600 + minutes * 60 + seconds

    // 프리셋 시간들 (초 단위)
    val presets = listOf(
        10 to "10초",
        30 to "30초",
        60 to "1분",
        180 to "3분",
        300 to "5분",
        600 to "10분"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "타이머 설정",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 현재 설정된 시간 표시
        Text(
            text = formatTime(totalSeconds),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 프리셋 버튼들
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presets.forEach { (secondsValue, label) ->
                AssistChip(
                    onClick = {
                        if (enabled) {
                            setTimeFromSeconds(
                                secondsValue,
                                onHoursChange,
                                onMinutesChange,
                                onSecondsChange
                            )
                        }
                    },
                    label = { Text(label) },
                    enabled = enabled
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 세밀 조정 버튼들
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // -10초
            FilledIconButton(
                onClick = {
                    if (enabled) {
                        val newTotal = maxOf(0, totalSeconds - 10)
                        setTimeFromSeconds(
                            newTotal,
                            onHoursChange,
                            onMinutesChange,
                            onSecondsChange
                        )
                    }
                },
                enabled = enabled && totalSeconds >= 10
            ) {
                Text("-10", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // -1초
            IconButton(
                onClick = {
                    if (enabled) {
                        val newTotal = maxOf(0, totalSeconds - 1)
                        setTimeFromSeconds(
                            newTotal,
                            onHoursChange,
                            onMinutesChange,
                            onSecondsChange
                        )
                    }
                },
                enabled = enabled && totalSeconds >= 1
            ) {
                Text("-1")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "초 단위",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(16.dp))

            // +1초
            IconButton(
                onClick = {
                    if (enabled) {
                        val newTotal = totalSeconds + 1
                        setTimeFromSeconds(
                            newTotal,
                            onHoursChange,
                            onMinutesChange,
                            onSecondsChange
                        )
                    }
                },
                enabled = enabled
            ) {
                Text("+1")
            }

            Spacer(modifier = Modifier.width(8.dp))

            // +10초
            FilledIconButton(
                onClick = {
                    if (enabled) {
                        val newTotal = totalSeconds + 10
                        setTimeFromSeconds(
                            newTotal,
                            onHoursChange,
                            onMinutesChange,
                            onSecondsChange
                        )
                    }
                },
                enabled = enabled
            ) {
                Text("+10", fontWeight = FontWeight.Bold)
            }
        }
    }
}

/**
 * 총 초를 시:분:초로 변환하여 설정
 */
private fun setTimeFromSeconds(
    totalSeconds: Int,
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit
) {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    onHoursChange(hours)
    onMinutesChange(minutes)
    onSecondsChange(seconds)
}

/**
 * 시간을 보기 좋게 포맷
 */
private fun formatTime(totalSeconds: Int): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return when {
        hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
        minutes > 0 -> String.format("%d:%02d", minutes, seconds)
        else -> "${seconds}초"
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInputSectionPreview10Seconds() {
    Kuit6_NotificationTheme {
        TimeInputSection(
            hours = 0,
            minutes = 0,
            seconds = 10,
            onHoursChange = {},
            onMinutesChange = {},
            onSecondsChange = {},
            enabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInputSectionPreview1Minute() {
    Kuit6_NotificationTheme {
        TimeInputSection(
            hours = 0,
            minutes = 1,
            seconds = 30,
            onHoursChange = {},
            onMinutesChange = {},
            onSecondsChange = {},
            enabled = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInputSectionPreviewDisabled() {
    Kuit6_NotificationTheme {
        TimeInputSection(
            hours = 0,
            minutes = 5,
            seconds = 0,
            onHoursChange = {},
            onMinutesChange = {},
            onSecondsChange = {},
            enabled = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}