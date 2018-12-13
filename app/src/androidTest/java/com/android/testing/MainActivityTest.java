package com.android.testing;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.PublicKey;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public MainActivity mainActivity;

    private String username_tobe_typed="ajesh@gmail.com";
    private String correct_password ="password";
    private String wrong_password = "passme123";

    @BeforeClass
    public static void startTesting(){
        Log.d("TAG","startTesting ");
    }

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class,true,true);


    @Before
    public void setUp(){
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("EXTRA", "Test");
        mainActivityActivityTestRule.launchActivity(intent);
        onView(withId(R.id.inEmail)).check(matches(withText("Enter your email here")));
    }

    @Test
    public void loginSuccess(){

        onView((withId(R.id.inEmail)))
                .perform(ViewActions.clearText(), closeSoftKeyboard());

        onView((withId(R.id.inEmail)))
                .perform(ViewActions.typeText(username_tobe_typed), closeSoftKeyboard());


        onView(withId(R.id.button))
                .perform(ViewActions.click());

        onView(withId(R.id.inEmail)).check(matches(withText(username_tobe_typed)));

    }

    @Test
    public void loginFailure(){
        Log.e("@Test","Performing login failure test");
        onView((withId(R.id.inEmail)))
                .perform(ViewActions.clearText(), closeSoftKeyboard());

        onView((withId(R.id.inEmail)))
                .perform(ViewActions.typeText(username_tobe_typed), closeSoftKeyboard());

        onView(withId(R.id.button))
                .perform(ViewActions.click());
    }

    @After
    public void tearDown(){
        mainActivity = null;
    }

    @AfterClass
    public static void stopTesting(){
        Log.d("TAG","stopTesting ");
    }


}