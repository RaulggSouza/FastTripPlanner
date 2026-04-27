package br.edu.ifsp.scl.sc3045153.fasttripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import br.edu.ifsp.scl.sc3045153.fasttripplanner.ui.theme.FastTripPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TripData(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TripData(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Dados da viagem", fontWeight = FontWeight.Bold, fontSize = 30.sp)

        var destination by rememberSaveable { mutableStateOf("") }
        var destinationError by rememberSaveable { mutableStateOf<String?>(null) }

        OutlinedTextField(
            value = destination,
            onValueChange = { newValue ->
                destination = newValue
                destinationError = validateDestination(newValue)
            },
            label = { Text("Destino") },
            isError = destinationError != null,
            supportingText = {
                destinationError?.let {
                    Text(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        var days by rememberSaveable { mutableStateOf("") }
        var daysError by rememberSaveable { mutableStateOf<String?>(null) }

        OutlinedTextField(
            value = days,
            onValueChange = { newValue -> days = newValue },
            label = { Text("Número de dias") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = daysError != null,
            supportingText = {
                daysError?.let { Text(it) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
    }
}

private fun validateDestination(value: String): String? {
    if (value.isBlank()) {
        return "Destination é obrigatório"
    }

    return null
}

private fun validateDays(value: String): String? {
    if (value.isBlank()) {
        return "Quantidade de dias é obrigatório"
    }

    if (!value.isDigitsOnly()) {
        return "Quantidade de dias deve ser númerico"
    }

    val days = value.toIntOrNull() ?: return "Digite uma quantidade de dias válida"

    if (days <= 0) {
        return "Quantidade de dias deve ser maior que zero"
    }

    return null
}

@Preview(showBackground = true)
@Composable
private fun TripDataPrev() {
    FastTripPlannerTheme {
        TripData()
    }
}