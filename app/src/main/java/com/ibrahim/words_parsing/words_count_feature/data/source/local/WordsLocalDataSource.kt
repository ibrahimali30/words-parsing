package com.ibrahim.words_parsing.words_count_feature.data.source.local

import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import com.ibrahim.words_parsing.words_count_feature.data.source.local.WordsDao
import io.reactivex.Single
import javax.inject.Inject


class WordsLocalDataSource @Inject constructor(
    private val wordsDao: WordsDao
) {

    fun getWordsFromLocalDB(): Single<List<WordsUiModel>> {
        return Single.fromCallable {
            wordsDao.getWordsFromLocalDB()
        }
    }

    fun insertWordsUiModel(wordsUiModel: List<WordsUiModel>) {
         wordsDao.refreshWords(wordsUiModel)
    }

}