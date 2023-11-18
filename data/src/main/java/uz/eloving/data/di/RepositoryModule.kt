package uz.eloving.data.di

import uz.eloving.data.repository.CurrentWeatherRepository
import uz.eloving.data.repository.CurrentWeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideCurrentWeatherRepo(impl : CurrentWeatherRepositoryImpl) : CurrentWeatherRepository

}