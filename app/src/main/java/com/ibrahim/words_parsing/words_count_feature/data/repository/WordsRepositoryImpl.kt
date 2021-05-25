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
                //map html response to counted words
                .map { responseBody ->
                    val regex = Regex("[^a-zA-Zء-ي0-9]")
                    val htmlString = responseBody.string().toString()
                        .replace(regex, " ") //remove Skip special characters
                        .replace("\\s+".toRegex(), " ") //remove all spaces
                        .trim()

                    val wordsMap = linkedMapOf<String, Int>()
                    htmlString.split(" ")
                        .forEach {
                            wordsMap[it] = (wordsMap[it] ?: 1) + 1
                    }

                    return@map wordsMap.map {
                        WordsUiModel(word = it.key, count = it.value)
                    }.also {
                        insertWordsIntoLocalDB(it)
                    }
                }
    }

    override fun getWordsFromLocalDB(): Single<List<WordsUiModel>> {
        return wordsLocalDataSource.getWordsByCityName()
    }

    override fun insertWordsIntoLocalDB(wordsUiModel: List<WordsUiModel>) {
        wordsLocalDataSource.insertWordsUiModel(wordsUiModel)
    }

}
