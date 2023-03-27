package com.ichi.moviedatabase

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ichi.moviedatabase.views.activities.HomeActivity
import com.ichi.moviedatabase.views.adapters.MoviesAdapter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieFunnelTest {
    @Before
    fun setUp() {
        launchActivity<HomeActivity>()
    }

    @Test
    fun testClickMovieAndDetailsAreShown() {
        onView(withId(R.id.movies_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<MoviesAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.overview_text_view)).check(matches(isDisplayed()))
    }
}
