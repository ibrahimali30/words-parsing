package com.ibrahim.words_parsing.words_count_feature.presentation.di


import com.ibrahim.words_parsing.db.AppDatabase
import com.ibrahim.words_parsing.words_count_feature.data.repository.WordsRepositoryImpl
import com.ibrahim.words_parsing.words_count_feature.data.source.local.WordsDao
import com.ibrahim.words_parsing.words_count_feature.data.source.remote.WordsApiService
import com.ibrahim.words_parsing.words_count_feature.domain.repsitory.WordsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class WordsModule {


    @Provides
    fun providesWordsRepository(placesRepositoryImpl: WordsRepositoryImpl): WordsRepository {
        return placesRepositoryImpl
    }

    @Provides
    fun providesWordsApiService(builder: Retrofit.Builder): WordsApiService {
        return builder.build().create(WordsApiService::class.java)
    }

    @Provides
    fun providesWordsDao(WeatherDatabase: AppDatabase): WordsDao {
        return WeatherDatabase.placesDao()
    }


}