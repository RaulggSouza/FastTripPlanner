package br.edu.ifsp.scl.sc3045153.fasttripplanner

import android.content.Intent
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
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

        //Recupera valores passados pela intent
        val destination = intent.getStringExtra("EXTRA_DESTINATION") ?: ""
        val days = intent.getIntExtra("EXTRA_DAYS", 0)
        val budget = intent.getDoubleExtra("EXTRA_BUDGET", 0.0)

        setContent {
            FastTripPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TripOptionsScreen(
                        modifier = Modifier.padding(innerPadding),
                        onBackClick = {
                            finish()
                        },
                        budget,
                        days,
                        destination
                    )
                }
            }

        }
    }
}

@Composable
fun TripOptionsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    budget: Double,
    days: Int,
    destination: String
) {
    val context = LocalContext.current
    //Tipo de acomadações marcado para não mudar quando a tela girar
    var selectedHostingType by rememberSaveable { mutableStateOf<HostingType?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Cria spacer para melhor visualização
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.width(250.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Escolha o tipo de hospedagem",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            //Itera por cada item do enum
            HostingType.entries.forEach { hostingType ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            //Faz texto do radio button ser clicável
                            selected = selectedHostingType == hostingType,
                            onClick = {
                                selectedHostingType = hostingType
                            },
                            role = Role.RadioButton
                        )
                ) {
                    //Cria um radio button para cada elemento do enum
                    RadioButton(
                        selected = selectedHostingType == hostingType,
                        onClick = null,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                    )

                    Text(hostingType.label, fontSize = 20.sp)
                }
            }
            if (selectedHostingType == null) {
                Text(
                    text = "Selecione sua hospedagem",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = modifier.height(20.dp))

        //Variável para Alimentação mantida ao girar tela
        var feedingIncluded by rememberSaveable { mutableStateOf(false) }
        //Variável para transporte mantida ao girar tela
        var transportIncluded by rememberSaveable { mutableStateOf(false) }
        //Variável para passeios mantida ao girar tela
        var tourGuideIncluded by rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = Modifier.width(250.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Escolha os serviços",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        //Faz texto do checkbox ser clicável
                        value = feedingIncluded,
                        onValueChange = { checked ->
                            feedingIncluded = checked
                        },
                        role = Role.Checkbox
                    )
                    .padding(vertical = 10.dp)
            ) {
                //Checkbox para alimentação
                Checkbox(
                    checked = feedingIncluded,
                    onCheckedChange = null,
                    Modifier.padding(horizontal = 10.dp)
                )

                Text("Alimentação inclusa", fontSize = 20.sp)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        //Faz texto do checkbox ser clicável
                        value = transportIncluded,
                        onValueChange = { checked ->
                            transportIncluded = checked
                        },
                        role = Role.Checkbox
                    )
                    .padding(vertical = 10.dp)
            ) {
                //Checkbox para transporte
                Checkbox(
                    checked = transportIncluded,
                    onCheckedChange = null,
                    Modifier.padding(horizontal = 10.dp)
                )

                Text("Transporte incluso", fontSize = 20.sp)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        //Faz texto do checkbox ser clicável
                        value = tourGuideIncluded,
                        onValueChange = { checked ->
                            tourGuideIncluded = checked
                        },
                        role = Role.Checkbox
                    )
                    .padding(vertical = 10.dp)
            ) {
                //Checkbox para passeios
                Checkbox(
                    checked = tourGuideIncluded,
                    onCheckedChange = null,
                    Modifier.padding(horizontal = 10.dp)
                )

                Text("Passeios inclusos", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = onBackClick, modifier = Modifier.padding(horizontal = 10.dp)) {
                Text("Voltar", fontSize = 20.sp)
            }

            Button(onClick = {
                //Verifica se uma hospedagem foi selecionada
                selectedHostingType?.let {
                    val intent = Intent(context, TripSummaryActivity::class.java).apply {
                        putExtra("EXTRA_DAYS", days)
                        putExtra("EXTRA_BUDGET", budget)
                        putExtra("EXTRA_DESTINATION", destination)
                        putExtra("EXTRA_HOSTING", it.multiplier)
                        putExtra("EXTRA_FEEDING", feedingIncluded)
                        putExtra("EXTRA_TRANSPORT", transportIncluded)
                        putExtra("EXTRA_TOUR", tourGuideIncluded)
                    }

                    context.startActivity(intent)
                }
            }, Modifier.padding(horizontal = 10.dp)) {
                Text("Calcular", fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripOptionsScreenPrev() {
    FastTripPlannerTheme {
        TripOptionsScreen(onBackClick = {}, budget = 11.0, days = 1, destination = "")
    }
}