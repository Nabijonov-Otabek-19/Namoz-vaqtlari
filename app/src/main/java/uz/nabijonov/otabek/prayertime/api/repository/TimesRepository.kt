package uz.nabijonov.otabek.prayertime.api.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import uz.nabijonov.otabek.prayertime.api.NetworkManager
import uz.nabijonov.otabek.prayertime.model.MonthlyModel
import uz.nabijonov.otabek.prayertime.model.WeeklyModel

class TimesRepository {

    private val compositeDisposable = CompositeDisposable()

    fun getWeeklyTimes(
        region: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<ArrayList<WeeklyModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getWeeklyTimes(region)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArrayList<WeeklyModel>>() {
                    override fun onNext(t: ArrayList<WeeklyModel>?) {
                        progress.value = false
                        if (t != null) {
                            success.value = t
                        }
                    }

                    override fun onError(e: Throwable?) {
                        progress.value = false
                        error.value = e?.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getMonthlyTimes(
        region: String,
        month: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<ArrayList<MonthlyModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getMonthlyTimes(region, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArrayList<MonthlyModel>>() {
                    override fun onNext(value: ArrayList<MonthlyModel>?) {
                        if (value != null) {
                            progress.value = false
                            success.value = value
                        }
                    }

                    override fun onError(e: Throwable?) {
                        progress.value = false
                        error.value = e?.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
}