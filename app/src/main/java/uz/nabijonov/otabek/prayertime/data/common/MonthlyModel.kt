package uz.nabijonov.otabek.prayertime.data.common

data class MonthlyModel(
    val date: String,
    val month: Int,
    val region: String,
    val times: Times,
    val weekday: String
)