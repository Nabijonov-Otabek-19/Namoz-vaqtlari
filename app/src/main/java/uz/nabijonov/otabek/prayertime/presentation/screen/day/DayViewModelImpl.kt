package uz.nabijonov.otabek.prayertime.presentation.screen.day

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.nabijonov.otabek.prayertime.data.common.CurrentData
import uz.nabijonov.otabek.prayertime.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class DayViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : DayViewModel, ViewModel() {

    override val progressData = MutableLiveData<Boolean>()
    override val errorData = MutableLiveData<String>()
    override val successData = MutableLiveData<CurrentData>()

    override fun loadData(region: String) {
        repository.getCurrentTimes(region)
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