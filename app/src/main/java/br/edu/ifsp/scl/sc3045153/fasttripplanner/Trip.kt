package br.edu.ifsp.scl.sc3045153.fasttripplanner

import java.io.Serializable

data class Trip(
    val destination: String,
    val days: Int,
    val budget: Double
): Serializable //Utilizando serializable para passar para outra intent, não estava conseguindo usar o parcelable
