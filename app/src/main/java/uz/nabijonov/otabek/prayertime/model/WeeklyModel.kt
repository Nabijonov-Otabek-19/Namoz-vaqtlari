package uz.nabijonov.otabek.prayertime.model

data class WeeklyModel(
    val date: String,
    val region: String,
    val times: Times,
    val weekday: String
)