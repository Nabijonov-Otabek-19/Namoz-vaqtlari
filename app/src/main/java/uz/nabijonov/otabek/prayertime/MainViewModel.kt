package uz.nabijonov.otabek.prayertime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.toolbox.StringRequest
import uz.nabijonov.otabek.prayertime.adapter.MonthlyAdapter
import uz.nabijonov.otabek.prayertime.api.repository.TimesRepository
import uz.nabijonov.otabek.prayertime.model.MonthlyModel
import uz.nabijonov.otabek.prayertime.model.WeeklyModel

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