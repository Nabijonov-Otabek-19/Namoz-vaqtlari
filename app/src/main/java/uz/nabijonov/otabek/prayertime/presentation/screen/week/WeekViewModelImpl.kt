package uz.nabijonov.otabek.prayertime.presentation.screen.week

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.nabijonov.otabek.prayertime.data.common.AllWeekData
import uz.nabijonov.otabek.prayertime.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class WeekViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : WeekViewModel, ViewModel() {

    override val progressData = MutableLiveData<Boolean>()
    override val errorData = MutableLiveData<String>()
    override val successData = MutableLiveData<AllWeekData>()

    override fun loadData(region: String) {
        repository.getWeeklyTimes(region)
            .onStart { progressData.value = true }
            .onEach { result ->
                result.onSuccess {
                    progressData.value = false
                    successData.value = it
                }
                result.onFailure {
                    progressData.value = false
                    errorData.value = it.message }
            }.launchIn(viewModelScope)
    }
}