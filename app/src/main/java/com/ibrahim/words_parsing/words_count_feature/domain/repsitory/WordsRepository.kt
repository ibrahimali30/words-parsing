package com.ibrahim.words_parsing.words_count_feature.domain.repsitory

import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import io.reactivex.Single

interface WordsRepository {

    fun fetchWords(): Single<List<WordsUiModel>>
    fun getWordsFromLocalDB(): Single<List<WordsUiModel>>
    fun insertWordsIntoLocalDB(wordsUiModel: List<WordsUiModel>)

}