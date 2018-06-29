package com.nbs.simplefootballapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.nbs.simplefootballapp.presentation.MainActivity;
import com.nbs.simplefootballapp.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.mCountingIdlingResource);
    }

    @After
    public void end(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.mCountingIdlingResource);
    }

    @Test
    public void testWhenRecyclerViewScrollingAndClick() {
        onView(ViewMatchers.withId(R.id.rvItems))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        onView(ViewMatchers.withId(R.id.rvItems))
                .perform(RecyclerViewActions.scrollToPosition(10));

        onView(ViewMatchers.withId(R.id.rvItems))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, ViewActions.click()));

        onView(withText("Leicester"))
                .inRoot(withDecorView(not(is(activityTestRule.getActivity().getWindow().getDecorView()))));
    }
}
