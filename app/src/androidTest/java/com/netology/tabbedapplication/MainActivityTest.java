package com.netology.tabbedapplication;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // --- Exercise 2. Task 1 ------------------------------------------------------------------
        // --- Elements "Tab1" exist on main screen and active
        ViewInteraction linearLayout = onView(
                allOf(withContentDescription("Tab 1"),
                        withParent(withParent(withId(R.id.tabs))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        // --- Elements "Tab1" contain text "TAB1"
        ViewInteraction textView = onView(
                allOf(withText("TAB 1"),
                        withParent(allOf(withContentDescription("Tab 1"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("TAB 1")));

        // --- Elements "Tab2" exist on main screen
        ViewInteraction linearLayout2 = onView(
                allOf(withContentDescription("Tab 2"),
                        withParent(withParent(withId(R.id.tabs))),
                        isDisplayed()));
        linearLayout2.check(matches(isDisplayed()));

        // --- Elements "Tab2" contain text "TAB2"
        ViewInteraction textView2 = onView(
                allOf(withText("TAB 2"),
                        withParent(allOf(withContentDescription("Tab 2"),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("TAB 2")));

        // --- Exercise 2. Task 2 ------------------------------------------------------------------
        // --- Element "TAB1" have elements with text "Page: 1"
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.section_label), withText("Page: 1"),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.view_pager)))),
                        isDisplayed()));
        textView3.check(matches(withText("Page: 1")));

        // --- Exercise 2. Task 3 ------------------------------------------------------------------
        // --- Click on element "TAB2"
        ViewInteraction tabView = onView(
                allOf(withContentDescription("Tab 2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        // Made visible elements with text "Page: 2"
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.section_label), withText("Page: 2"),
                        withParent(allOf(withId(R.id.constraintLayout),
                                withParent(withId(R.id.view_pager)))),
                        isDisplayed()));
        textView5.check(matches(withText("Page: 2")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
