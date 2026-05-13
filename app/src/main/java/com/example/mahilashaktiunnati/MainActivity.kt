package com.example.mahilashaktiunnati

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mahilashaktiunnati.ui.theme.MahilaShaktiUnnatiTheme

/**
 * Main Activity — entry point of the application.
 * Sets up the Compose theme and navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MahilaShaktiUnnatiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val app = applicationContext as MahilaShaktiUnnatiApp
                    AppNavigation(app)
                }
            }
        }
    }
}
