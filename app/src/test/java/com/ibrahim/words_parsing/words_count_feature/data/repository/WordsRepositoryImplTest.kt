package com.ibrahim.words_parsing.words_count_feature.data.repository

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.ibrahim.words_parsing.db.AppDatabase
import com.ibrahim.words_parsing.utils.RxSchedulerRule
import com.ibrahim.words_parsing.words_count_feature.data.source.local.WordsDao
import com.ibrahim.words_parsing.words_count_feature.data.source.local.WordsLocalDataSource
import com.ibrahim.words_parsing.words_count_feature.data.source.remote.WordsRemoteDataSource
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class WordsRepositoryImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    @MockK
    lateinit var wordsLocalDataSource: WordsLocalDataSource
    @MockK
    lateinit var wordsRemoteDataSource: WordsRemoteDataSource
    private lateinit var wordsDatabase: AppDatabase
    private lateinit var wordsDao: WordsDao
    private lateinit var wordsRepository: WordsRepositoryImpl

    private val wordsMok = WordsUiModel(0,"html",1)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        wordsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        wordsDao = wordsDatabase.wordsDao()
        wordsRepository = WordsRepositoryImpl(wordsRemoteDataSource, wordsLocalDataSource)

    }

    @Test
    fun testGetWordsFromLocalDB() {
        every {
            wordsLocalDataSource.getWordsFromLocalDB()
        } returns Single.just(listOf(wordsMok))

        wordsRepository.getWordsFromLocalDB()
            .subscribe { wordItemsList, t2 ->
                Truth.assertThat(wordItemsList[0].id).isEqualTo(wordsMok.id)
            }

        verify { wordsLocalDataSource.getWordsFromLocalDB() }
    }

    private val htmlResponse = "<!doctype html> <html lang= ar "

    @Test
    fun testFetchWords() {
        every {
            wordsRemoteDataSource.fetchWords()
        } returns Single.just(htmlResponse)
            .delay(1,TimeUnit.SECONDS)

        wordsRepository.fetchWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({wordItemsList->
                Truth.assertThat(wordItemsList.size).isEqualTo(4)
                Truth.assertThat(wordItemsList[0].word).isEqualTo("doctype")
            },{
                it
            })

        verify { wordsRemoteDataSource.fetchWords() }
    }
}