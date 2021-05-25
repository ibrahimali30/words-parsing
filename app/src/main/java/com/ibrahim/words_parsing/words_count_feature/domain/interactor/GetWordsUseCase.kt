package com.ibrahim.words_parsing.words_count_feature.domain.interactor


import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import com.ibrahim.words_parsing.words_count_feature.domain.repsitory.WordsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(private val wordsRepository: WordsRepository) {

    fun fetchWords(): Single<List<WordsUiModel>> {
        return wordsRepository.fetchWords()
    }

    fun getWordsFromLocalDB(): Single<List<WordsUiModel>> {
        return wordsRepository.getWordsFromLocalDB()
    }

}