package com.example.kuit6_notification.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kuit6_notification.ui.theme.Kuit6_NotificationTheme

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hours
            TimeInputField(
                value = hours,
                onValueChange = { value ->
                    if (value in 0..23) onHoursChange(value)
                },
                label = "시간",
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(":", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.width(8.dp))

            // Minutes
            TimeInputField(
                value = minutes,
                onValueChange = { value ->
                    if (value in 0..59) onMinutesChange(value)
                },
                label = "분",
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(":", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.width(8.dp))

            // Seconds
            TimeInputField(
                value = seconds,
                onValueChange = { value ->
                    if (value in 0..59) onSecondsChange(value)
                },
                label = "초",
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TimeInputField(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value.toString().padStart(2, '0'),
        onValueChange = { newValue ->
            val filtered = newValue.filter { it.isDigit() }
            if (filtered.isNotEmpty()) {
                filtered.toIntOrNull()?.let { onValueChange(it) }
            } else {
                onValueChange(0)
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        enabled = enabled,
        textStyle = MaterialTheme.typography.titleLarge.copy(
            textAlign = TextAlign.Center
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TimeInputSectionEnabledPreview() {
    Kuit6_NotificationTheme {
        TimeInputSection(
            hours = 0,
            minutes = 1,
            seconds = 30,
            onHoursChange = {},
            onMinutesChange = {},
            onSecondsChange = {},
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeInputSectionDisabledPreview() {
    Kuit6_NotificationTheme {
        TimeInputSection(
            hours = 0,
            minutes = 5,
            seconds = 0,
            onHoursChange = {},
            onMinutesChange = {},
            onSecondsChange = {},
            enabled = false
        )
    }
}
