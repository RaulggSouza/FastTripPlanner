package br.edu.ifsp.scl.sc3045153.fasttripplanner

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3045153.fasttripplanner.ui.theme.FastTripPlannerTheme

class TripSummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
                        {
                            val intent =
                                Intent(this@TripSummaryActivity, TripDataActivity::class.java).apply {
                                    flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                }

                            startActivity(intent)
                            finish()
                        },
                        Trip(
                            destination,
                            days,
                            budget,
                            hostingType,
                            feedingIncluded,
                            transportIncluded,
                            tourGuideIncluded
                        ),
                        totalTripCost
                    )
                }
            }
        }
    }
}

@Composable
fun TripSummaryScreen(
    modifier: Modifier = Modifier,
    onRestartClick: () -> Unit,
    trip: Trip,
    totalTripCost: Double
) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dados da viagem",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        //Monta os dados da viagem
        Column(modifier = Modifier
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
            .padding(10.dp)) {
            Text("Destino: ${trip.destination}")
            Text("Dias: ${trip.days}")
            Text("Orçamento: R$${trip.budget}")
            Text("Hospedagem: ${trip.hostingType.label}")
            Text("Alimentação inclusa: ${if (trip.feedingIncluded) "Sim" else "Não"}")
            Text("Transporte incluso: ${if (trip.transportIncluded) "Sim" else "Não"}")
            Text("Passeios inclusos: ${if (trip.tourGuideIncluded) "Sim" else "Não"}")
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Mostra total da viagem
        Text(
            text = "Total da viagem",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Text("R$${totalTripCost}", fontSize = 20.sp, modifier = Modifier.padding(10.dp))

        Button(onClick = onRestartClick) {
            Text("Reiniciar")
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun TripSummaryPreview() {
    TripSummaryScreen(
        onRestartClick = {},
        trip =
            Trip(
                "SP",
                1,
                1.0,
                HostingType.ECONOMIC,
                feedingIncluded = false,
                transportIncluded = false,
                tourGuideIncluded = false
            ),
        totalTripCost = 100.0
    )
}