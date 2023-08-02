package uz.nabijonov.otabek.prayertime.data.source.remote.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.nabijonov.otabek.prayertime.data.common.AllMonthData
import uz.nabijonov.otabek.prayertime.data.common.AllWeekData
import uz.nabijonov.otabek.prayertime.data.common.CurrentData

interface Api {

    @GET("present/day?")
    suspend fun getCurrentTimes(@Query("region") reg: String?): Response<CurrentData>

    @GET("present/week?")
    suspend fun getWeeklyTimes(@Query("region") reg: String?): Response<AllWeekData>

    @GET("monthly?")
    suspend fun getMonthlyTimes(
        @Query("region") reg: String?,
        @Query("month") month: Int
    ): Response<AllMonthData>
}