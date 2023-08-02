package uz.nabijonov.otabek.prayertime.data.common

data class WeeklyItem(
    val date: String,
    val region: String,
    val times: Times,
    val weekday: String
)