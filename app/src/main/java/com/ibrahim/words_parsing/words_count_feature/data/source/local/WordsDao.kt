package com.ibrahim.words_parsing.words_count_feature.data.source.local

import androidx.room.*
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel


@Dao
interface WordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWordsUiModel(words: List<WordsUiModel>)

    @Query("select * from WordsUiModel")
    fun getWordsFromLocalDB(): List<WordsUiModel>

    @Query("delete from WordsUiModel")
    fun deleteAllWords()

    @Transaction
    fun refreshWords(words: List<WordsUiModel>) {
        deleteAllWords()
        insertWordsUiModel(words)
    }

}