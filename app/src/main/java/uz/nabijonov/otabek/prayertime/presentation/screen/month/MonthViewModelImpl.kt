package uz.nabijonov.otabek.prayertime.presentation.screen.month

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.nabijonov.otabek.prayertime.data.common.AllMonthData
import uz.nabijonov.otabek.prayertime.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class MonthViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : MonthViewModel, ViewModel() {

    override val progressData = MutableLiveData<Boolean>()
    override val errorData = MutableLiveData<String>()
    override val successData = MutableLiveData<AllMonthData>()

    override fun loadData(region: String, month: Int) {
        repository.getMonthlyTimes(region, month)
            .onStart { progressData.value = true }
            .onEach { result ->
                result.onSuccess {
                    progressData.value = false
                    successData.value = it
                }
                result.onFailure {
                    progressData.value = false
                    errorData.value = it.message
                }
            }.launchIn(viewModelScope)
    }
}