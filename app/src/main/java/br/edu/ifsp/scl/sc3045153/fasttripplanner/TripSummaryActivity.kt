package br.edu.ifsp.scl.sc3045153.fasttripplanner

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifsp.scl.sc3045153.fasttripplanner.ui.theme.FastTripPlannerTheme

class TripSummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val destination = intent.getStringExtra("EXTRA_DESTINATION") ?: ""
        val days = intent.getIntExtra("EXTRA_DAYS", 0)
        val budget = intent.getDoubleExtra("EXTRA_BUDGET", 0.0)

        //Verificação para pegar serialized, eu não conesegui fazer o parcelized funcionar
        val hostingType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_HOSTING", HostingType::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_HOSTING") as? HostingType
        }

        if (hostingType == null) {
            Toast.makeText(
                this,
                "Erro ao carregar dados",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        val feedingIncluded = intent.getBooleanExtra("EXTRA_FEEDING", false)
        val transportIncluded = intent.getBooleanExtra("EXTRA_TRANSPORT", false)
        val tourGuideIncluded = intent.getBooleanExtra("EXTRA_TOUR", false)

        val baseTripCost = days * budget * hostingType.multiplier
        var totalTripCost = baseTripCost

        if (feedingIncluded) totalTripCost += 50 * days
        if (transportIncluded) totalTripCost += 300
        if (tourGuideIncluded) totalTripCost += 120 * days

        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TripSummaryScreen(
                        modifier = Modifier.padding(innerPadding),
                        Trip(
                            destination,
                            days,
                            budget,
                            hostingType,
                            feedingIncluded,
                            transportIncluded,
                            tourGuideIncluded
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TripSummaryScreen(modifier: Modifier = Modifier, trip: Trip) {

}

@Preview(showBackground = true)
@Composable
private fun TripSummaryPreview() {
    TripSummaryScreen(
        trip =
            Trip(
                "",
                1,
                1.0,
                HostingType.ECONOMIC,
                feedingIncluded = false,
                transportIncluded = false,
                tourGuideIncluded = false
            )
    )
}