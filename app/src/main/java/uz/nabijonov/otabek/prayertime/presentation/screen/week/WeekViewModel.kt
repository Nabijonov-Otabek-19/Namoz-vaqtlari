package uz.nabijonov.otabek.prayertime.presentation.screen.week

import androidx.lifecycle.LiveData
import uz.nabijonov.otabek.prayertime.data.common.AllWeekData

interface WeekViewModel {
    val progressData: LiveData<Boolean>
    val errorData: LiveData<String>
    val successData: LiveData<AllWeekData>

    fun loadData(region: String)
}