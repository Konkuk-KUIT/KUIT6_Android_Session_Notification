package com.example.kuit6_notification.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kuit6_notification.model.TimerState
import com.example.kuit6_notification.ui.components.TimeInputSection
import com.example.kuit6_notification.ui.components.TimerControls
import com.example.kuit6_notification.ui.components.TimerDisplay
import com.example.kuit6_notification.ui.theme.Kuit6_NotificationTheme
import com.example.kuit6_notification.viewmodel.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    viewModel: TimerViewModel = viewModel()
) {
    val timerState by viewModel.timerState.collectAsState()
    val hours by viewModel.hours.collectAsState()
    val minutes by viewModel.minutes.collectAsState()
    val seconds by viewModel.seconds.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("타이머") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                TimerDisplay(
                    timerState = timerState,
                    initialMillis = viewModel.getInitialMillis()
                )
            }

            if (timerState is TimerState.Idle) {
                TimeInputSection(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                    onHoursChange = viewModel::setHours,
                    onMinutesChange = viewModel::setMinutes,
                    onSecondsChange = viewModel::setSeconds,
                    enabled = true
                )

                Spacer(modifier = Modifier.height(32.dp))
            }

            TimerControls(
                timerState = timerState,
                onStart = viewModel::startTimer,
                onPause = viewModel::pauseTimer,
                onReset = viewModel::resetTimer,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
    Kuit6_NotificationTheme {
        TimerScreen()
    }
}
