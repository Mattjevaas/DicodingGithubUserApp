package com.example.gihubuserapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun assertMainViewDisplay() {
        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        onView(withId(R.id.setting_page)).check(matches(isDisplayed()))
        onView(withId(R.id.bookmark_page)).check(matches(isDisplayed()))
    }
}