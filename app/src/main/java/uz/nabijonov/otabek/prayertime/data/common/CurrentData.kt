package uz.nabijonov.otabek.prayertime.data.common

data class CurrentData(
    val date: String,
    val region: String,
    val times: Times,
    val weekday: String
)