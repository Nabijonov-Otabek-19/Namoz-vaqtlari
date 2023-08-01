package uz.nabijonov.otabek.prayertime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.nabijonov.otabek.prayertime.domain.repository.TimesRepository
import uz.nabijonov.otabek.prayertime.data.common.MonthlyModel
import uz.nabijonov.otabek.prayertime.data.common.WeeklyModel

class MainViewModel : ViewModel() {

    private val repository = TimesRepository()

    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val weeklyTimes = MutableLiveData<ArrayList<WeeklyModel>>()
    val monthlyTimes = MutableLiveData<ArrayList<MonthlyModel>>()


    fun getWeeklyTimes(region: String) {
        repository.getWeeklyTimes(region, error, progress, weeklyTimes)
    }

    fun getMonthlyTimes(region: String, month: Int) {
        repository.getMonthlyTimes(region, month, error, progress, monthlyTimes)
    }
}