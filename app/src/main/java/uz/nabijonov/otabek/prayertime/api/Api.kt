package uz.nabijonov.otabek.prayertime.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import uz.nabijonov.otabek.prayertime.model.MonthlyModel
import uz.nabijonov.otabek.prayertime.model.WeeklyModel
import kotlin.collections.ArrayList

interface Api {
    @GET("present/week?")
    fun getWeeklyTimes(@Query("region") reg: String?): Observable<ArrayList<WeeklyModel>>

    @GET("monthly?")
    fun getMonthlyTimes(
        @Query("region") reg: String?,
        @Query("month") month: Int
    ): Observable<ArrayList<MonthlyModel>>
}