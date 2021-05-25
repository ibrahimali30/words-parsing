package com.ibrahim.words_parsing.words_count_feature.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import com.ibrahim.words_parsing.words_count_feature.domain.interactor.GetWordsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject


class WordsViewModel @Inject constructor(
        private val wordsUseCase: GetWordsUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val screenState by lazy { MutableLiveData<WordsScreenState>() }

    fun getWords() {
        screenState.value = WordsScreenState.Loading
        wordsUseCase.fetchWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleSuccess(it)
            }, {
                handleErrorLoadingFromRemote(it)
            }).addTo(compositeDisposable)
    }

    private fun getCashedWords() {
        screenState.value = WordsScreenState.Loading

        wordsUseCase.getWordsFromLocalDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleLocalData(it)
            }, {
                handleErrorLoadingFromLocal(it)
            }).addTo(compositeDisposable)
    }

    private fun handleLocalData(it: List<WordsUiModel>) {
        if (it.isNotEmpty()) {
            screenState.value = WordsScreenState.SuccessLocalResponse(it)
        } else {
            screenState.value = WordsScreenState.ErrorLoadingFromLocal(Exception("empty result"))
        }
    }

    private fun handleErrorLoadingFromLocal(it: Throwable) {
        screenState.value = WordsScreenState.ErrorLoadingFromLocal(it)
    }

    private fun handleErrorLoadingFromRemote(it: Throwable) {
        getCashedWords()
        screenState.value = WordsScreenState.ErrorLoadingFromApi(it)
    }

    private fun handleSuccess(it: List<WordsUiModel>) {
        screenState.value = WordsScreenState.SuccessAPIResponse(it)
    }

    sealed class WordsScreenState {
        object Loading : WordsScreenState()
        class ErrorLoadingFromApi(val error: Throwable) : WordsScreenState()
        class ErrorLoadingFromLocal(val error: Throwable) : WordsScreenState()
        class SuccessAPIResponse(val data: List<WordsUiModel>) : WordsScreenState()
        class SuccessLocalResponse(val data: List<WordsUiModel>) : WordsScreenState()

    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}