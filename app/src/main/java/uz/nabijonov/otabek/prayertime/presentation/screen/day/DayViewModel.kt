package uz.nabijonov.otabek.prayertime.presentation.screen.day

import androidx.lifecycle.LiveData
import uz.nabijonov.otabek.prayertime.data.common.CurrentData

interface DayViewModel {
    val progressData: LiveData<Boolean>
    val errorData: LiveData<String>
    val successData: LiveData<CurrentData>

    fun loadData(region: String)
}