package uz.nabijonov.otabek.prayertime.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.nabijonov.otabek.prayertime.data.common.*

interface AppRepository {

    fun getCurrentTimes(region: String): Flow<Result<CurrentData>>

    fun getWeeklyTimes(region: String): Flow<Result<AllWeekData>>

    fun getMonthlyTimes(region: String, month: Int): Flow<Result<AllMonthData>>
}