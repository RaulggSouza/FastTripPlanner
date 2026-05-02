package br.edu.ifsp.scl.sc3045153.fasttripplanner

data class Trip(
    val destination: String,
    val days: Int,
    val budget: Double,
    val hostingType: HostingType,
    val feedingIncluded: Boolean,
    val transportIncluded: Boolean,
    val tourGuideIncluded: Boolean,
)
