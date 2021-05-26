package com.ibrahim.words_parsing.words_count_feature.data.source.remote

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.ibrahim.words_parsing.utils.RxSchedulerRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import okhttp3.*
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class WordsRemoteDataSourceTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    lateinit var wordsLocalDataSource: WordsRemoteDataSource
    @MockK
    private lateinit var wordsApiService: WordsApiService
    val mokedResponseString = "doctype html  html lang ar data n head" // test response html
    val responseBody = ResponseBody.create(MediaType.parse("application/json"), mokedResponseString)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        wordsLocalDataSource =  WordsRemoteDataSource(wordsApiService)
    }


    @Test
    fun fetchWords() {

        every {
            wordsApiService.getWords()
        } returns Single.just(responseBody)

        wordsLocalDataSource.fetchWords()
            .subscribe { stringResponse, t2 ->
                Truth.assertThat(stringResponse).isEqualTo(mokedResponseString)
            }

        verify { wordsApiService.getWords() }
    }
}