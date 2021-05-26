package com.ibrahim.words_parsing.words_count_feature.data.source.local

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.ibrahim.words_parsing.db.AppDatabase
import com.ibrahim.words_parsing.utils.RxSchedulerRule
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class WordsLocalDataSourceTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
    lateinit var wordsLocalDataSource: WordsLocalDataSource
    private lateinit var wordsDatabase: AppDatabase
    private lateinit var wordsDao: WordsDao
    
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        wordsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        wordsDao = wordsDatabase.wordsDao()
        wordsLocalDataSource =  WordsLocalDataSource(wordsDao)
    }

    private val wordsMok = WordsUiModel(0,"html",1)


  @Test
    fun testGetWordsFromLocalDB() {
      wordsDao.insertWordsUiModel(listOf(wordsMok))
      // Then
      val value = wordsDao.getWordsFromLocalDB()
      Truth.assertThat(value[0].word).isEqualTo(wordsMok.word)

      wordsLocalDataSource.getWordsFromLocalDB()
          .subscribe { wordItemsList, t2 ->
              Truth.assertThat(wordItemsList[0].word).isEqualTo(wordsMok.word)
          }

    }

    @Test
    fun testInsertWordsUiModel() {
        wordsDao.refreshWords(listOf(wordsMok))
        // Then
        val value = wordsDao.getWordsFromLocalDB()
        Truth.assertThat(value[0].word).isEqualTo(wordsMok.word)

        wordsLocalDataSource.getWordsFromLocalDB()
            .subscribe { wordItemsList, t2 ->
                Truth.assertThat(wordItemsList[0].word).isEqualTo(wordsMok.word)
            }
    }
}