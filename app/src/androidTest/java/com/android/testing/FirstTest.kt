package com.android.testing

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.RootMatchers.isPlatformPopup
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FirstTest {

    @Rule
    @JvmField
    public var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {

    }

    @Test
    fun testEditTextDisplayed(){
        onView(withId(R.id.inEmail)).check(matches(isDisplayed()))
    }

    @Test
    fun testButtonDisplayed(){
        onView(withId(R.id.button)).check(matches(isDisplayed()))
    }

    @Test
    fun testButtonRoot(){
        onView(withId(R.id.button)).inRoot(isPlatformPopup())
    }

    @Test
    fun testEditTextType(){
        onView(withId(R.id.inEmail)).perform(ViewActions.clearText(),closeSoftKeyboard())

        onView(withId(R.id.inEmail)).perform(ViewActions.typeText("Punam I Love You"),closeSoftKeyboard())
    }

    @Test
    fun testButtonText(){
        onView(withId(R.id.button)).check(matches(withText("CHECK")))
        onView(withText("CHECK1")).check(doesNotExist())
    }

    @Test
    fun testButtonClick(){
        onView(withId(R.id.button)).perform(ViewActions.click())
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown(){

    }

}