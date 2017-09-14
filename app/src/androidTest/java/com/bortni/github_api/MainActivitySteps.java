package com.bortni.github_api;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.bortni.github_api.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivitySteps {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests significantly
     * more reliable.
     */

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(
                mActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void dataIsLoadedOnTheScreen() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.text_id), withText(TestConsts.FIRST_VISIBLE_ITEM), isDisplayed()));
        textView.check(matches(withText(TestConsts.FIRST_VISIBLE_ITEM)));
    }

    @Test
    public void listScrollingDownAndNextElementLoading() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(14));

        registerIdlingResource();

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(15));

        ViewInteraction textView = onView(
                allOf(withId(R.id.text_id), withText(TestConsts.FIRST_VISIBLE_ITEM_ON_SECOND_PAGE), isDisplayed()));
        textView.check(matches(withText(TestConsts.FIRST_VISIBLE_ITEM_ON_SECOND_PAGE)));
    }

    @Test
    public void listScrollingDownToTheEndAndAllDataLoaded() {
        for (int i = 0; i < 7; i++) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition
                            (mActivityTestRule.getActivity().recyclerView.getAdapter().getItemCount() - 1));

            registerIdlingResource();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.text_id), withText(TestConsts.LAST_VISIBLE_ITEM_ON_LAST_PAGE), isDisplayed()));
        textView.check(matches(withText(TestConsts.LAST_VISIBLE_ITEM_ON_LAST_PAGE)));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(
                mActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
