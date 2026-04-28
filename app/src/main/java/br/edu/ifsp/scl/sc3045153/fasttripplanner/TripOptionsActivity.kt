package br.edu.ifsp.scl.sc3045153.fasttripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifsp.scl.sc3045153.fasttripplanner.ui.theme.FastTripPlannerTheme

class TripOptionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                }
            }
        }
    }
}

@Composable
fun TripOptionsScreen(modifier: Modifier = Modifier) {
    var selectedOption by rememberSaveable { mutableStateOf<HostingType?>(null) }
}

@Preview
@Composable
private fun TripOptionsScreenPrev() {
    FastTripPlannerTheme {
        TripOptionsScreen()
    }
}