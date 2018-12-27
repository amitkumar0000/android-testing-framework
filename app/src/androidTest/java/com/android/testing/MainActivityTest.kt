package com.android.testing

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.TextView

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    var mainActivity: MainActivity? = null

    private val username_tobe_typed = "$%#$#aj12esh@gmail.com"
    private val correct_password = "password"
    private val wrong_password = "passme123"

    @Rule
    @JvmField
    var mainActivityActivityTestRule = ActivityTestRule(MainActivity::class.java, true, true)


    @Before
    @Throws(Exception::class)
    fun setUp() {
        mainActivity = mainActivityActivityTestRule.activity
    }

    @Test
    fun demonstrateIntentPrep() {
        val intent = Intent()
        intent.putExtra("EXTRA", "Test")
        mainActivityActivityTestRule.launchActivity(intent)
        onView(withId(R.id.inEmail)).check(matches(withText("Enter your email here")))
    }

    @Test
    fun loginSuccess() {

        onView(withId(R.id.inEmail))
                .perform(ViewActions.clearText(), closeSoftKeyboard())

        onView(withId(R.id.inEmail))
                .perform(typeText(username_tobe_typed), closeSoftKeyboard())


        onView(withId(R.id.button))
                .perform(click())


        val appCompatImageView = onView(
                allOf(withId(R.id.search_button), withContentDescription("Search"),
                        childAtPosition(
                                allOf(withId(R.id.search_bar),
                                        childAtPosition(
                                                withId(R.id.searchView),
                                                0)),
                                1),
                        isDisplayed()))
        appCompatImageView.perform(click())

        val searchAutoComplete = onView(allOf(withId(R.id.search_src_text),
                childAtPosition(allOf(withId(R.id.search_plate),
                        childAtPosition(withId(R.id.search_edit_frame), 1)), 0), isDisplayed()))

        searchAutoComplete.perform(ViewActions.replaceText("s"),pressImeActionButton())


        onView(withId(R.id.callBtn)).check(matches(isDisplayed()))

        onView(withId(R.id.callBtn)).perform(click())

    }


    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }


    @Test
    fun loginFailure() {
        Log.e("@Test", "Performing login failure test")
        onView(withId(R.id.inEmail))
                .perform(ViewActions.clearText(), closeSoftKeyboard())

        onView(withId(R.id.inEmail))
                .perform(typeText(username_tobe_typed), closeSoftKeyboard())

        onView(withId(R.id.button))
                .perform(click())
    }

    @After
    fun tearDown() {
        mainActivity = null
    }

    companion object {

        @BeforeClass
        fun startTesting() {
            Log.d("TAG", "startTesting ")
        }

        @AfterClass
        fun stopTesting() {
            Log.d("TAG", "stopTesting ")
        }
    }


}