package com.ibrahim.words_parsing


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ibrahim.words_parsing.words_count_feature.domain.entity.WordsUiModel
import com.ibrahim.words_parsing.words_count_feature.presentation.viewmodel.WordsViewModel
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    val adapter by lazy { mActivityTestRule.activity.adapter }
    val viewmodel by lazy { mActivityTestRule.activity.wordsViewModel }

    val list by lazy {
        mutableListOf(
            WordsUiModel(0,"word 1",1),
            WordsUiModel(1,"word 2",2),
            WordsUiModel(2,"word 3",3),
            WordsUiModel(3,"word 4",4),
            WordsUiModel(4,"word 5",5)
        )
    }

    @Test
    fun test_loading_visibility() {
        //test loading visibility
        viewmodel.screenState.postValue(WordsViewModel.WordsScreenState.Loading)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_binding_data_to_recycle_view_from_local_source() {
        //test binding data to recycler view from local source
        val wordModelLocal = list[2]
        viewmodel.screenState.postValue(
            WordsViewModel.WordsScreenState.SuccessLocalResponse(listOf(wordModelLocal))
        )
        //check word title
        onView(
            allOf(
                withId(R.id.tvWordTitle),
                withParent(withParent(withId(R.id.rvWords)))
            )
        ).check(matches(withText(wordModelLocal.word)))

        //check word count
        onView(
            allOf(
                withId(R.id.tvWordCount),
                withParent(withParent(withId(R.id.rvWords)))
            )
        ).check(matches(withText(wordModelLocal.count.toString())))
    }

    @Test
    fun test_binding_data_to_recycle_view_from_remote_source() {
        //test binding data to recycler view from remote source
        val wordModelRemote = list[2]
        viewmodel.screenState.postValue(WordsViewModel.WordsScreenState.SuccessAPIResponse(listOf(wordModelRemote)))
        //check word title
        onView(
            allOf(
                withId(R.id.tvWordTitle),
                withParent(withParent(withId(R.id.rvWords)))
            )
        ).check(matches(withText(wordModelRemote.word)))

        //check word count
        onView(
            allOf(
                withId(R.id.tvWordCount),
                withParent(withParent(withId(R.id.rvWords)))
            )
        ).check(matches(withText(wordModelRemote.count.toString())))
    }

   @Test
    fun test_error_view_visibility() {
        // on Error check if error view and retry button is displayed
        viewmodel.screenState.postValue(
            WordsViewModel.WordsScreenState.ErrorLoadingFromLocal(Exception("test exception msg"))
        )
        onView(withId(R.id.tvErrorMsg)).check(matches(isDisplayed()))
        onView(withId(R.id.btRetry)).check(matches(isDisplayed()))
       // TODO: 5/26/2021 test btRetry on click
    }
}
