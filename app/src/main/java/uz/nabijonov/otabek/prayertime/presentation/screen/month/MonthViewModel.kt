package uz.nabijonov.otabek.prayertime.presentation.screen.month

import androidx.lifecycle.LiveData
import uz.nabijonov.otabek.prayertime.data.common.AllMonthData

interface MonthViewModel {
    val progressData: LiveData<Boolean>
    val errorData: LiveData<String>
    val successData: LiveData<AllMonthData>

    fun loadData(region: String, month: Int)
}