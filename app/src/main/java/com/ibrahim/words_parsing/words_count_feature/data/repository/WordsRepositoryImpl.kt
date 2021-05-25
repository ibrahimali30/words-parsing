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
                .map { it ->
                    val regex = Regex("[^a-zA-Zء-ي0-9]")
                    var text = it.string().toString()
                        .replace(regex, " ")//remove Skip special characters
                        .replace("\\s+".toRegex(), " ") //remove all spaces
                        .trim()

                    val map = hashMapOf<String, Int>()
                    text.split(" ")
                        .forEach {
                            if (map.containsKey(it)){
                                map[it] = map[it]!! + 1
                            }else {
                                map[it] = 1
                            }
                    }

                    val v = map.map {
                        WordsUiModel(word = it.key, count = it.value)
                    }

                    return@map v
                }
    }

    override fun getWordsFromLocalDB(): Single<List<WordsUiModel>> {
        return wordsLocalDataSource.getWordsByCityName()
    }

    override fun insertWordsIntoLocalDB(wordsUiModel: List<WordsUiModel>) {
        wordsLocalDataSource.insertWordsUiModel(wordsUiModel)
    }

}
