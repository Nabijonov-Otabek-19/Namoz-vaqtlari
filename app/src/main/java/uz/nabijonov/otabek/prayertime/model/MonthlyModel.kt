package uz.nabijonov.otabek.prayertime.model

data class MonthlyModel(
    val date: String,
    val month: Int,
    val region: String,
    val times: Times,
    val weekday: String
)