package br.edu.ifsp.scl.sc3045153.fasttripplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
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
    //Contexto da Intent
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Dados da viagem", fontWeight = FontWeight.Bold, fontSize = 30.sp)

        //Valor da destination, marcado para não mudar ao virar a tela
        var destination by rememberSaveable { mutableStateOf("") }
        //Erro de destination
        var destinationError by rememberSaveable { mutableStateOf<String?>(null) }

        //Campo de texto para destination
        OutlinedTextField(
            value = destination,
            onValueChange = { newValue ->
                destination = newValue
                //Chama função para validação de destination
                destinationError = validateDestination(newValue)
            },
            label = { Text("Destino") },
            //Adiciona erro no campo de texto dependendo da validação
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

        //Valor de days, marcado para não mudar ao virar a tela
        var days by rememberSaveable { mutableStateOf("") }
        //Erro de days
        var daysError by rememberSaveable { mutableStateOf<String?>(null) }

        //Campo de texto para days
        OutlinedTextField(
            value = days,
            onValueChange = { newValue ->
                days = newValue
                //Chama função para validação de days
                daysError = validateDays(newValue)
            },
            label = { Text("Número de dias") },
            //Modifica keyboard para numérico
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            //Adiciona erro no campo de texto dependendo da validação
            isError = daysError != null,
            supportingText = {
                daysError?.let { Text(it) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        //Valor de budget, marcado para não mudar ao virar a tela
        var budget by rememberSaveable { mutableStateOf("") }
        //Erro de budget
        var budgetError by rememberSaveable { mutableStateOf<String?>(null) }

        //Campo de texto para budget
        OutlinedTextField(
            value = budget,
            onValueChange = { newValue ->
                budget = newValue
                //Chama função para validação de budget
                budgetError = validateBudget(newValue)
            },
            label = { Text("Orçamento diário") },
            //Modifica keyboard para decimal
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            //Adiciona erro no campo de texto dependendo da validação
            isError = budgetError != null,
            supportingText = {
                budgetError?.let { Text(it) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        Button(onClick = {
            //Valida uma última vez ao clicar
            destinationError = validateDestination(destination)
            daysError = validateDays(days)
            budgetError = validateBudget(budget)

            //Transforma valores string para números
            val daysNumber = days.toIntOrNull()
            val budgetNumber = budget.replace(",", ".").toDoubleOrNull()

            //Se tudo estiver certo, chama a intent
            if(
                destinationError == null &&
                daysError == null &&
                budgetError == null &&
                daysNumber != null &&
                budgetNumber != null
            ) {
                val intent = Intent(context, TripOptions::class.java). apply {
                    putExtra("EXTRA_DESTINATION", destination.trim())
                    putExtra("EXTRA_DAYS", daysNumber)
                    putExtra("EXTRA_BUDGET", budgetNumber)
                }
                context.startActivity(intent)
            }
        }, modifier = Modifier.padding(5.dp)) {
                Text("Avançar")
        }
    }
}

private fun validateDestination(value: String): String? {
    //Valida se destination é vazio
    if (value.isBlank()) {
        return "Destino é obrigatório"
    }

    return null
}

private fun validateDays(value: String): String? {
    //Verifica se days é vazio
    if (value.isBlank()) {
        return "Quantidade de dias é obrigatório"
    }

    //Verifica se days é formado somente por dígitos
    if (!value.isDigitsOnly()) {
        return "Quantidade de dias deve ser númerico"
    }

    //Transforma days para int para validação, se for null mostra erro de dias inválidos
    val days = value.toIntOrNull() ?: return "Digite uma quantidade de dias válida"

    //Verifica se dias é menor que 0
    if (days <= 0) {
        return "Quantidade de dias deve ser maior que zero"
    }

    return null
}

private fun validateBudget(value: String): String? {
    //Verifica se days é vazio
    if (value.isBlank()) {
        return "Orçamento é obrigatório"
    }

    //Troca , por . e transforma para double para validação, se for null mostra erro de budget inválido
    val budget = value
        .replace(',', '.')
        .toDoubleOrNull() ?: return "Digite um orçamento válido"

    //Verifica se dias é menor que 0;0
    if (budget <= 0.0) {
        return "Orçamento deve ser maior que zero"
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