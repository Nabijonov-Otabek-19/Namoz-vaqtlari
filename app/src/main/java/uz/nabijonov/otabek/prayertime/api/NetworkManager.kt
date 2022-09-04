package uz.nabijonov.otabek.prayertime.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.nabijonov.otabek.prayertime.utils.Constansts

object NetworkManager {
    private var retrofit: Retrofit? = null

    private var api: Api? = null

    fun getApiService(): Api {
        if (api == null) {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constansts.WEEK_URL).build()

            api = retrofit!!.create(Api::class.java)
        }
        return api!!
    }
}