package com.ibrahim.words_parsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import com.ibrahim.words_parsing.words_count_feature.presentation.adapter.WordsAdapter
import com.ibrahim.words_parsing.words_count_feature.presentation.viewmodel.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error_view.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var wordsViewModel: WordsViewModel

    val adapter: WordsAdapter by lazy { WordsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordsViewModel.getWords()

        observeScreenState()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        rvWords.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvWords.adapter = adapter
    }

    private fun observeScreenState() {
        wordsViewModel.screenState.observe(this , Observer {
            onScreenStateChanged(it)
        })
    }

    private fun onScreenStateChanged(state: WordsViewModel.WordsScreenState?) {
        handleErrorViewsVisibility(state is WordsViewModel.WordsScreenState.ErrorLoadingFromLocal)
        handleLoadingVisibility(state == WordsViewModel.WordsScreenState.Loading)

        when (state) {
            is WordsViewModel.WordsScreenState.SuccessAPIResponse -> handleSuccess(state.data)
            is WordsViewModel.WordsScreenState.ErrorLoadingFromLocal -> handleError(state.error)
            is WordsViewModel.WordsScreenState.SuccessLocalResponse ->{
                handleSuccess(state.data)
            }
            else -> {}
        }
    }

    private fun handleErrorViewsVisibility(show: Boolean) {
        errorViewLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun handleError(error: Throwable) {
        errorViewLayout.btRetry.setOnClickListener {
            wordsViewModel.getWords()
        }
    }

    private fun handleLoadingVisibility(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    private fun handleSuccess(data: List<WordsUiModel>) {
        adapter.setList(data)
    }

}