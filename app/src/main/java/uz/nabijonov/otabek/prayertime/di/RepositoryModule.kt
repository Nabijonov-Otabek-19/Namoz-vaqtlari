package uz.nabijonov.otabek.prayertime.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.nabijonov.otabek.prayertime.domain.repository.AppRepository
import uz.nabijonov.otabek.prayertime.domain.repository.AppRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindNewsRepository(impl: AppRepositoryImpl): AppRepository
}