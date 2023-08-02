package uz.nabijonov.otabek.prayertime.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.nabijonov.otabek.prayertime.data.common.*
import uz.nabijonov.otabek.prayertime.data.source.remote.api.Api
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: Api
) : AppRepository {

    override fun getCurrentTimes(region: String): Flow<Result<CurrentData>> = flow {
        val response = api.getCurrentTimes(region)

        when (response.code()) {
            in 200..299 -> {
                emit(Result.success(response.body() as CurrentData))
            }

            else -> {
                emit(Result.failure(Exception(response.errorBody()?.string())))
            }
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun getWeeklyTimes(region: String): Flow<Result<AllWeekData>> = flow {
        val response = api.getWeeklyTimes(region)

        when (response.code()) {
            in 200..299 -> {
                emit(Result.success(response.body() as AllWeekData))
            }

            else -> {
                emit(Result.failure(Exception(response.errorBody()?.string())))
            }
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun getMonthlyTimes(region: String, month: Int): Flow<Result<AllMonthData>> = flow {
        val response = api.getMonthlyTimes(region, month)

        when (response.code()) {
            in 200..299 -> {
                emit(Result.success(response.body() as AllMonthData))
            }

            else -> {
                emit(Result.failure(Exception(response.errorBody()?.string())))
            }
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)
}