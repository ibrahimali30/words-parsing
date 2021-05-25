package com.ibrahim.words_parsing.words_count_feature.data.repository

import com.ibrahim.words_parsing.words_count_feature.data.source.local.WordsLocalDataSource
import com.ibrahim.words_parsing.words_count_feature.data.source.remote.WordsRemoteDataSource
import com.ibrahim.words_parsing.words_count_feature.domain.repsitory.WordsRepository
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import io.reactivex.Single
import javax.inject.Inject


class WordsRepositoryImpl @Inject constructor(
    private val wordsRemoteDataSource: WordsRemoteDataSource,
    private val wordsLocalDataSource: WordsLocalDataSource
) : WordsRepository {

    override fun fetchWords(): Single<List<WordsUiModel>> {
        return wordsRemoteDataSource.fetchWords()
                .map {
                    // TODO: 5/25/2021 map to list of WordsUiModel
                    null
                }
    }

    override fun getWordsFromLocalDB(): Single<List<WordsUiModel>> {
        return wordsLocalDataSource.getWordsByCityName()
    }

    override fun insertWordsIntoLocalDB(wordsUiModel: List<WordsUiModel>) {
        wordsLocalDataSource.insertWordsUiModel(wordsUiModel)
    }

}
