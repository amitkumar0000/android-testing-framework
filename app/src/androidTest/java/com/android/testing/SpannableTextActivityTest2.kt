package com.android.testing


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SpannableTextActivityTest2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SpannableTextActivity::class.java)

    @Test
    fun spannableTextActivityTest2() {
        val appCompatTextView = onView(
                allOf(withId(R.id.spannableText), withText("To style text in Android, use spans! Chan....more"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatTextView.perform(click())

        val appCompatTextView2 = onView(
                allOf(withId(R.id.spannableText), withText("To style text in Android, use spans! Change the color of a few characters, make them clickable, scale the size of the text or even draw custom bullet points with spans. Spans can change the TextPaint properties, draw on a Canvas, or even change text layout and affect elements like the line height. Spans are markup objects that can be attached to and detached from text; they can be applied to whole paragraphs or to parts of the text....less"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()))
        appCompatTextView2.perform(click())
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
}
