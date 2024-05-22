package com.example.vodafonetask.di

import android.content.Context
import androidx.room.Room
import com.example.data.Repository.CityRepositoryImp
import com.example.data.Repository.WeatherRepositoryImp
import com.example.data.local.CityDao
import com.example.data.local.CityDataBase
import com.example.data.local.LocalSourceImp
import com.example.data.local.LocalSourceInterface
import com.example.data.remote.RemoteSourceImp
import com.example.data.remote.RemoteSourceInterface
import com.example.data.remote.Services
import com.example.domain.repository.CityRepositoryInterface
import com.example.domain.repository.WeatherRepositoryInterface
import com.example.domain.usecase.GetAllCitiesUseCase
import com.example.domain.usecase.GetWeatherUseCase
import com.example.domain.usecase.InsertCityUseCase
import com.example.domain.usecase.SearchForCityByNameUseCae
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BASIC
    )

    const val provideUrl =
        "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun provideCityDatabase(@ApplicationContext context: Context): CityDataBase =
        Room.databaseBuilder(context, CityDataBase::class.java, "Database").build()

    @Provides
    @Singleton
    fun provideCityDao(dataBase: CityDataBase): CityDao = dataBase.getCityDao()

    @Provides
    @Singleton
    fun provideLocalSourceImp(universityDAO: CityDao): LocalSourceInterface {
        return LocalSourceImp(universityDAO)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(logging).connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): Services {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).baseUrl(provideUrl).build().create(Services::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteSource(services: Services): RemoteSourceInterface =
        RemoteSourceImp(services)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        remoteSourceInterface: RemoteSourceInterface
    ): WeatherRepositoryInterface =
        WeatherRepositoryImp(remoteSourceInterface)

    @Provides
    @Singleton
    fun provideCityRepository(
        localSourceInterface: LocalSourceInterface
    ): CityRepositoryInterface =
        CityRepositoryImp(localSourceInterface)

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(repository: WeatherRepositoryInterface): GetWeatherUseCase =
        GetWeatherUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCityInfoUseCase(repository: WeatherRepositoryInterface): SearchForCityByNameUseCae =
        SearchForCityByNameUseCae(repository)

    @Provides
    @Singleton
    fun provideGetCityUseCase(repository: CityRepositoryInterface): GetAllCitiesUseCase =
        GetAllCitiesUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertCityUseCase(repository: CityRepositoryInterface): InsertCityUseCase =
        InsertCityUseCase(repository)

}