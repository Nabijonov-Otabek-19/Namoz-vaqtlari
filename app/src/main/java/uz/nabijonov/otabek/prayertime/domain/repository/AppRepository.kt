package uz.nabijonov.otabek.prayertime.domain.repository

interface AppRepository {

    fun getTodayInfos()
    fun getWeeklyInfos()
    fun getMonthlyInfos()
}