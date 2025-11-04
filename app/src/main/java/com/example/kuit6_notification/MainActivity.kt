package com.example.kuit6_notification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kuit6_notification.ui.screen.TimerScreen
import com.example.kuit6_notification.ui.theme.Kuit6_NotificationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kuit6_NotificationTheme {
                TimerScreen()
            }
        }
    }
}