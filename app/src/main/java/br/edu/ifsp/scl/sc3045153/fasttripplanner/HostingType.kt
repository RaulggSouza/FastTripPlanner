package br.edu.ifsp.scl.sc3045153.fasttripplanner

enum class HostingType(val label: String, val multiplier: Double) {
    ECONOMIC("Econômica", 1.0),
    COMFORT("Conforto", 1.5),
    LUXURY("Luxo", 2.2)
}