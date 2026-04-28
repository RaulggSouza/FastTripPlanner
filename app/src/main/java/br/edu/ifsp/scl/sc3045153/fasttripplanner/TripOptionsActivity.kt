package br.edu.ifsp.scl.sc3045153.fasttripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3045153.fasttripplanner.ui.theme.FastTripPlannerTheme

class TripOptionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TripOptionsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TripOptionsScreen(modifier: Modifier = Modifier) {
    //Tipo de acomadações marcado para não mudar quando a tela girar
    var selectedHostingType by rememberSaveable { mutableStateOf<HostingType?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Cria spacer para melhor visualização
        Spacer(modifier = modifier.height(20.dp))

        Text(
            "Escolha o tipo de hospedagem",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
        )

        //Itera por cada item do enum
        HostingType.entries.forEach { hostingType ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.width(200.dp)
            ) {
                //Cria um radio button para cada elemento do enum
                RadioButton(
                    selected = selectedHostingType == hostingType,
                    onClick = {
                        selectedHostingType = hostingType
                    }
                )

                Text(hostingType.label, fontSize = 20.sp)
            }
        }

        Spacer(modifier = modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun TripOptionsScreenPrev() {
    FastTripPlannerTheme {
        TripOptionsScreen()
    }
}