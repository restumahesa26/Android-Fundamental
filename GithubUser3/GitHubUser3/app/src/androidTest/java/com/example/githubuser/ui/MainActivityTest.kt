package com.example.githubuser.ui

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuser.R
import com.example.githubuser.adapter.ListUserAdapter
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import kotlin.reflect.typeOf

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val username = "restumahesa26"

    @Before
    fun onCreate() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testTypeAtSearchView() {
        Espresso.onView(ViewMatchers.withId(R.id.searchView)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchView)).perform(ViewActions.typeText(username), ViewActions.closeSoftKeyboard())
    }
}